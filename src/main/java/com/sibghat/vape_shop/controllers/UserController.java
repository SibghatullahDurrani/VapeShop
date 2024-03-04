package com.sibghat.vape_shop.controllers;

import com.sibghat.vape_shop.controllers.interfaces.IUserController;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdatePasswordDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import com.sibghat.vape_shop.services.user.IUserServices;
import com.sibghat.vape_shop.services.user.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements IUserController {

    private final IUserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }


    @Override
    public ResponseEntity<GetUserDto> addUser(AddUserDto userToAdd) {
        return userServices.addUser(userToAdd);
    }

    @Override
    public ResponseEntity<HttpStatus> verifyUser(@PathVariable String verification_code){
        return userServices.verifyUser(verification_code);
    }

   @Override
    public ResponseEntity<GetUserDto> getUser(@PathVariable String username){
        return userServices.getUser(username);
    }

    @Override
    public ResponseEntity<GetUserDto> updateUser(
            @PathVariable String username,
            @Valid @RequestBody UpdateUserDto userToUpdate
    ){
        return userServices.updateUser(username, userToUpdate);
    }

    @Override
    public ResponseEntity<HttpStatus> updatePassword(
            @PathVariable String username,
            @Valid @RequestBody UpdatePasswordDto updatePasswordDto
    ) {
        return null;
    }
}
