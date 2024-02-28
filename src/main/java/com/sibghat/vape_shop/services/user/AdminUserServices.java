package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
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
public class AdminUserServices implements IAdminUserServices{

    private final UserRepository userRepository;
    private final AddUserMapper addUserMapper;
    private final GetUserMapper getUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final IUserRelatedConditionEvaluators userRelatedConditionEvaluators;

    public AdminUserServices(
            UserRepository userRepository,
            AddUserMapper addUserMapper,
            GetUserMapper getUserMapper,
            PasswordEncoder passwordEncoder,
            UserRelatedConditionEvaluators userRelatedConditionEvaluators) {
        this.userRepository = userRepository;
        this.addUserMapper = addUserMapper;
        this.getUserMapper = getUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRelatedConditionEvaluators = userRelatedConditionEvaluators;
    }

    @Override
    public AddUserDto addAdmin(AddUserDto adminToAdd, String CreatedBy) {
        userRelatedConditionEvaluators.checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser(adminToAdd);
        User user = addUserMapper.mapTo(adminToAdd);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setCreatedBy(CreatedBy);
        user.setRole("ROLE_ADMIN");
        return addUserMapper.mapFrom(userRepository.save(user));
    }

    @Override
    public ResponseEntity<GetUserDto> getAdmin(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        return user.map(value -> new ResponseEntity<>(getUserMapper.mapFrom(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
