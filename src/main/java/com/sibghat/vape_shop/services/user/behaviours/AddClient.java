package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.mappers.user.AddUserDtoToUserMapper;
import com.sibghat.vape_shop.mappers.user.UserToGetUserDtoMapper;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.conditionEvaluators.UserRelatedConditionEvaluators;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.IAddBehaviour;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.ISendVerificationEmailBehaviour;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.UUID;

@Service
public class AddClient implements IAddBehaviour {
    
    private final UserRelatedConditionEvaluators userRelatedConditionEvaluators;
    private final AddUserDtoToUserMapper addUserDtoToUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserToGetUserDtoMapper userToGetUserDtoMapper;
    private final UserRepository userRepository;
    private final ISendVerificationEmailBehaviour sendVerificationEmailBehaviour;

//    @Value("${emailVerificationURL}")
    private String emailVerificationURL = "http://127.0.0.1:4200/verifyemail/";

    public AddClient(
            UserRelatedConditionEvaluators userRelatedConditionEvaluators,
            AddUserDtoToUserMapper addUserDtoToUserMapper,
            PasswordEncoder passwordEncoder,
            UserToGetUserDtoMapper userToGetUserDtoMapper,
            UserRepository userRepository,
            ISendVerificationEmailBehaviour sendVerificationEmailBehaviour
    ) {
        this.userRelatedConditionEvaluators = userRelatedConditionEvaluators;
        this.addUserDtoToUserMapper = addUserDtoToUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.userToGetUserDtoMapper = userToGetUserDtoMapper;
        this.userRepository = userRepository;
        this.sendVerificationEmailBehaviour = sendVerificationEmailBehaviour;
    }

    @Override
    public ResponseEntity<@Valid GetUserDto> add(AddUserDto userToAdd, String createdBy) throws MessagingException, UnsupportedEncodingException {
        int VERIFICATION_CODE_EXPIRATION_TIME_IN_SECONDS = 600;
        userRelatedConditionEvaluators.checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser(userToAdd);
        User user = addUserDtoToUserMapper.mapFrom(userToAdd);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setCreatedBy(createdBy);
        user.setVerificationCode(UUID.randomUUID().toString());
        user.setVerificationCodeValidTill(Instant.now().getEpochSecond() +
                VERIFICATION_CODE_EXPIRATION_TIME_IN_SECONDS);
        GetUserDto savedUser = userToGetUserDtoMapper.mapFrom(userRepository.save(user));
        sendVerificationEmailBehaviour.sendVerificationEmail(savedUser,emailVerificationURL);
        return new ResponseEntity<>(
                savedUser,
                HttpStatus.CREATED
        );
    }
}
