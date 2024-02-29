package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.mappers.user.AddUserMapper;
import com.sibghat.vape_shop.mappers.user.GetUserMapper;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.conditionEvaluators.IUserRelatedConditionEvaluators;
import com.sibghat.vape_shop.services.conditionEvaluators.UserRelatedConditionEvaluators;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServices implements IUserServices {

    private final UserRepository userRepository;
    private final AddUserMapper addUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final IUserRelatedConditionEvaluators userRelatedConditionEvaluators;
    private final GetUserMapper getUserMapper;

    public UserServices(
            UserRepository userRepository,
            AddUserMapper addUserMapper,
            PasswordEncoder passwordEncoder,
            UserRelatedConditionEvaluators userRelatedConditionEvaluators,
            GetUserMapper getUserMapper
    ) {
        this.userRepository = userRepository;
        this.addUserMapper = addUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRelatedConditionEvaluators = userRelatedConditionEvaluators;
        this.getUserMapper = getUserMapper;
    }

    @Override
    public ResponseEntity<AddUserDto> addUser(AddUserDto addUserDto) {
        userRelatedConditionEvaluators.checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser(addUserDto);
        User user = addUserMapper.mapTo(addUserDto);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setCreatedBy(addUserDto.getUsername());

        return new ResponseEntity<>(addUserMapper.mapFrom(userRepository.save(user)), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<GetUserDto> getUser(String username) {
        Optional<GetUserDto> user = userRepository.findUserByUsername(username);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
