package com.sibghat.vape_shop.services.conditionEvaluators;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Component;

@Component
public class UserRelatedConditionEvaluators implements IUserRelatedConditionEvaluators{

    private final UserRepository userRepository;

    public UserRelatedConditionEvaluators(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser(AddUserDto userToAdd) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean check = true;
        if (userRepository.existsByContactNumber(userToAdd.getContactNumber())){
            stringBuilder.append("contactNumber ");
            check = false;
        }
        if(userRepository.existsByEmail(userToAdd.getEmail())){
            stringBuilder.append("email ");
            check = false;
        }
        if(userRepository.existsByUsername(userToAdd.getUsername())){
            stringBuilder.append("username ");
            check = false;
        }
        if(!check){
            throw new EntityExistsException(stringBuilder.toString());
        }
    }
}
