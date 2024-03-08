package com.sibghat.vape_shop.controllers.user;

import com.sibghat.vape_shop.controllers.user.interfaces.IClientController;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdatePasswordDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import com.sibghat.vape_shop.services.user.ClientServices;
import com.sibghat.vape_shop.services.user.interfaces.IClientServices;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class ClientController implements IClientController {

    private final IClientServices clientServices;

    public ClientController(ClientServices clientServices) {
        this.clientServices = clientServices;
    }


    @Override
    public ResponseEntity<GetUserDto> addUser(AddUserDto userToAdd) throws MessagingException, UnsupportedEncodingException {
        String username = userToAdd.getUsername();
        return clientServices.addUser(userToAdd,username);
    }

    @Override
    public ResponseEntity<HttpStatus> verifyUser(@PathVariable String verification_code){
        return clientServices.verifyUser(verification_code);
    }

    @Override
    public ResponseEntity<HttpStatus> enableAccount(@PathVariable String username) throws MessagingException, UnsupportedEncodingException {
        return clientServices.enableAccount(username);
    }

   @Override
    public ResponseEntity<GetUserDto> getUser(@PathVariable String username){
        return clientServices.getUser(username);
    }

    @Override
    public ResponseEntity<GetUserDto> updateUser(
            @PathVariable String username,
            @Valid @RequestBody UpdateUserDto userToUpdate
    ){
        return clientServices.updateUser(username, userToUpdate);
    }

    @Override
    public ResponseEntity<HttpStatus> updatePassword(
            @PathVariable String username,
            @Valid @RequestBody UpdatePasswordDto updatePasswordDto
    ) {
        return clientServices.updatePassword(username,updatePasswordDto);
    }

    @Override
    public ResponseEntity<HttpStatus> disableClient(@PathVariable String username){
        return clientServices.disableUser(username);
    }
}
