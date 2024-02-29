package com.sibghat.vape_shop.controllers;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import com.sibghat.vape_shop.services.user.IUserServices;
import com.sibghat.vape_shop.services.user.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final IUserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/users")
    public ResponseEntity<GetUserDto> addUser(@Valid @RequestBody AddUserDto addUserDto){
        return userServices.addUser(addUserDto);
    }

    @PatchMapping("/users/verify/{verification_code}")
    public ResponseEntity<HttpStatus> verifyUser(@PathVariable String verification_code){
        return userServices.verifyUser(verification_code);
    }

    @GetMapping("/users/{username}")
    @PreAuthorize("""
    #username == authentication.name and
    hasRole("USER")
""")
    @PostAuthorize("""
    returnObject.body.username == authentication.name
""")
    public ResponseEntity<GetUserDto> getUser(@PathVariable String username){
        return userServices.getUser(username);
    }

    @PutMapping("/users/{username}")
    @PreAuthorize("""
    #username == authentication.name and
    hasRole("USER")
""")
    @PostAuthorize("""
    returnObject.body.username == authentication.name
""")
    public ResponseEntity<GetUserDto> updateUser(
            @PathVariable String username,
            @RequestBody UpdateUserDto userToUpdate
    ){
        return userServices.updateUser(username, userToUpdate);
    }
}
