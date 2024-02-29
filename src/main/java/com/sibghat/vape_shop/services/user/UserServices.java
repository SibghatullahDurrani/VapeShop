package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import com.sibghat.vape_shop.mappers.user.AddUserDtoToUserMapper;
import com.sibghat.vape_shop.mappers.user.UserToGetUserDtoMapper;
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
    private final AddUserDtoToUserMapper addUserDtoToUserMapper;
    private final UserToGetUserDtoMapper userToGetUserDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final IUserRelatedConditionEvaluators userRelatedConditionEvaluators;

    public UserServices(
            UserRepository userRepository,
            AddUserDtoToUserMapper addUserDtoToUserMapper,
            UserToGetUserDtoMapper userToGetUserDtoMapper,
            PasswordEncoder passwordEncoder,
            UserRelatedConditionEvaluators userRelatedConditionEvaluators
    ) {
        this.userRepository = userRepository;
        this.addUserDtoToUserMapper = addUserDtoToUserMapper;
        this.userToGetUserDtoMapper = userToGetUserDtoMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRelatedConditionEvaluators = userRelatedConditionEvaluators;
    }

    @Override
    public ResponseEntity<GetUserDto> addUser(AddUserDto addUserDto) {
        userRelatedConditionEvaluators.checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser(addUserDto);
        User user = addUserDtoToUserMapper.mapFrom(addUserDto);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setCreatedBy(addUserDto.getUsername());

        return new ResponseEntity<>(userToGetUserDtoMapper.mapFrom(userRepository.save(user)), HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<GetUserDto> getUser(String username) {
        Optional<GetUserDto> user = userRepository.getUserByUsername(username);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<GetUserDto> updateUser(String username, UpdateUserDto userToUpdateDto) {
//        User userToUpdate = updateUserDtoToUserMapper.mapFrom(userToUpdateDto);
        Optional<User> userToUpdate = userRepository.findUserByUsername(username);
        if(userToUpdate.isPresent()){
            userToUpdate.get().setFirstName(userToUpdateDto.getFirstName());
            userToUpdate.get().setLastName(userToUpdateDto.getLastName());
            userToUpdate.get().setContactNumber(userToUpdateDto.getContactNumber());
            userRepository.save(userToUpdate.get());

            return new ResponseEntity<>(userToGetUserDtoMapper.mapFrom(userToUpdate.get()),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
