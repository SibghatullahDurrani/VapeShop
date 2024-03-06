package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdatePasswordDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public abstract class UserServices {
    private final IAddBehaviour addBehaviour;
    private final IVerifyUserBehaviour verifyUserBehaviour;
    private final IGetUserBehaviour getUserBehaviour;
    private final IUpdateUserBehaviour updateUserBehaviour;
    private final IUpdatePasswordBehaviour updatePasswordBehaviour;

    protected UserServices(
            IAddBehaviour addBehaviour,
            IVerifyUserBehaviour verifyUserBehaviour,
            IGetUserBehaviour getUserBehaviour,
            IUpdateUserBehaviour updateUserBehaviour,
            IUpdatePasswordBehaviour updatePasswordBehaviour
    ) {
        this.addBehaviour = addBehaviour;
        this.verifyUserBehaviour = verifyUserBehaviour;
        this.getUserBehaviour = getUserBehaviour;
        this.updateUserBehaviour = updateUserBehaviour;
        this.updatePasswordBehaviour = updatePasswordBehaviour;
    }

    public ResponseEntity<GetUserDto> addUser(AddUserDto userToAdd, String createdBy){
        return addBehaviour.add(userToAdd,createdBy);
    }

    public ResponseEntity<HttpStatus> verifyUser(String verification_code){
        return verifyUserBehaviour.verifyUser(verification_code);
    }

    public ResponseEntity<GetUserDto> getUser(String username){
        return getUserBehaviour.getUser(username);
    }

    public ResponseEntity<GetUserDto> updateUser(String username, UpdateUserDto userToUpdate){
        return updateUserBehaviour.updateUser(username,userToUpdate);
    }

    public ResponseEntity<HttpStatus> updatePassword(String username, UpdatePasswordDto updatePasswordDto){
        return updatePasswordBehaviour.updatePassword(username,updatePasswordDto);
    }

}
