package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.mappers.user.AddUserDtoToUserMapper;
import com.sibghat.vape_shop.mappers.user.UserToGetUserDtoMapper;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.conditionEvaluators.UserRelatedConditionEvaluators;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.IAddBehaviour;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AddClient implements IAddBehaviour {
    
    private final UserRelatedConditionEvaluators userRelatedConditionEvaluators;
    private final AddUserDtoToUserMapper addUserDtoToUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserToGetUserDtoMapper userToGetUserDtoMapper;
    private final UserRepository userRepository;

    public AddClient(
            UserRelatedConditionEvaluators userRelatedConditionEvaluators,
            AddUserDtoToUserMapper addUserDtoToUserMapper,
            PasswordEncoder passwordEncoder,
            UserToGetUserDtoMapper userToGetUserDtoMapper,
            UserRepository userRepository
    ) {
        this.userRelatedConditionEvaluators = userRelatedConditionEvaluators;
        this.addUserDtoToUserMapper = addUserDtoToUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.userToGetUserDtoMapper = userToGetUserDtoMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<GetUserDto> add(AddUserDto userToAdd, String createdBy) {
        int VERIFICATION_CODE_EXPIRATION_TIME_IN_SECONDS = 600;
        userRelatedConditionEvaluators.checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser(userToAdd);
        User user = addUserDtoToUserMapper.mapFrom(userToAdd);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setCreatedBy(createdBy);
        user.setVerificationCode(UUID.randomUUID().toString());
        user.setVerificationCodeValidTill(Instant.now().getEpochSecond() +
                VERIFICATION_CODE_EXPIRATION_TIME_IN_SECONDS);
        return new ResponseEntity<>(
                userToGetUserDtoMapper.mapFrom(userRepository.save(user)),
                HttpStatus.CREATED
        );
    }
}
