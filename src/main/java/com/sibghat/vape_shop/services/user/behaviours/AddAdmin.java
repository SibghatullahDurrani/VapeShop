package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.mappers.user.AddUserDtoToUserMapper;
import com.sibghat.vape_shop.mappers.user.UserToGetUserDtoMapper;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.conditionEvaluators.IUserRelatedConditionEvaluators;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.IAddBehaviour;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.ISendVerificationEmailBehaviour;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.UUID;

@Service
public class AddAdmin implements IAddBehaviour {

    private final UserRepository userRepository;
    private final AddUserDtoToUserMapper addUserDtoToUserMapper;
    private final UserToGetUserDtoMapper userToGetUserDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final IUserRelatedConditionEvaluators userRelatedConditionEvaluators;
    private final ISendVerificationEmailBehaviour sendVerificationEmailBehaviour;

//    @Value("${emailVerificationURL}")
    private String emailVerificationURL = "localhost:3000/users/verify/";

    public AddAdmin(
            UserRepository userRepository,
            AddUserDtoToUserMapper addUserDtoToUserMapper,
            UserToGetUserDtoMapper userToGetUserDtoMapper,
            PasswordEncoder passwordEncoder,
            IUserRelatedConditionEvaluators userRelatedConditionEvaluators,
            ISendVerificationEmailBehaviour sendVerificationEmailBehaviour
    ) {
        this.userRepository = userRepository;
        this.addUserDtoToUserMapper = addUserDtoToUserMapper;
        this.userToGetUserDtoMapper = userToGetUserDtoMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRelatedConditionEvaluators = userRelatedConditionEvaluators;
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
        user.setRole("ROLE_ADMIN");
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
