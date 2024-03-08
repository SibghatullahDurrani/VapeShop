package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import com.sibghat.vape_shop.mappers.user.UserToGetUserDtoMapper;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.conditionEvaluators.UserRelatedConditionEvaluators;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.IUpdateUserBehaviour;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UpdateUser implements IUpdateUserBehaviour {

    private final UserRepository userRepository;
    private final UserRelatedConditionEvaluators userRelatedConditionEvaluators;
    private final UserToGetUserDtoMapper userToGetUserDtoMapper;

    public UpdateUser(UserRepository userRepository, UserRelatedConditionEvaluators userRelatedConditionEvaluators, UserToGetUserDtoMapper userToGetUserDtoMapper) {
        this.userRepository = userRepository;
        this.userRelatedConditionEvaluators = userRelatedConditionEvaluators;
        this.userToGetUserDtoMapper = userToGetUserDtoMapper;
    }

    @Override
    public ResponseEntity<@Valid GetUserDto> updateUser(String username, UpdateUserDto userToUpdateDto) {
        Optional<User> userToUpdate = userRepository.findUserByUsername(username);
        userRelatedConditionEvaluators
                .checkThatContactNumberDoesNotAlreadyExistsBeforeUpdatingUser(userToUpdateDto.getContactNumber());
        if(userToUpdate.isPresent()){
            userToUpdate.get().setFirstName(userToUpdateDto.getFirstName());
            userToUpdate.get().setLastName(userToUpdateDto.getLastName());
            userToUpdate.get().setContactNumber(userToUpdateDto.getContactNumber());
            userToUpdate.get().setLastModifiedAt(LocalDateTime.now());
            userToUpdate.get().setLastModifiedBy(username);
            User savedUser = userRepository.save(userToUpdate.get());

            return new ResponseEntity<>(userToGetUserDtoMapper.mapFrom(savedUser),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
