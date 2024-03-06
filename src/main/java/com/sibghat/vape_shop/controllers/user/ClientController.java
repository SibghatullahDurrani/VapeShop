package com.sibghat.vape_shop.controllers.user;

import com.sibghat.vape_shop.controllers.user.interfaces.IClientController;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdatePasswordDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import com.sibghat.vape_shop.services.user.interfaces.IClientServices;
import com.sibghat.vape_shop.services.user.ClientServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController implements IClientController {

    private final IClientServices userServices;

    public ClientController(ClientServices clientServices) {
        this.userServices = clientServices;
    }


    @Override
    public ResponseEntity<GetUserDto> addUser(AddUserDto userToAdd) {
        String username = userToAdd.getUsername();
        return userServices.addUser(userToAdd,username);
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
        return userServices.updatePassword(username,updatePasswordDto);
    }
}
