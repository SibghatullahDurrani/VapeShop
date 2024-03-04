package com.sibghat.vape_shop.controllers.interfaces;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

public interface IUserController {

    @PostMapping("/users")
    ResponseEntity<GetUserDto> addUser(@Valid @RequestBody AddUserDto userToAdd);

    @PatchMapping("/users/verify/{verification_code}")
    ResponseEntity<HttpStatus> verifyUser(@PathVariable String verification_code);

    @GetMapping("/users/{username}")
    @PreAuthorize("""
    #username == authentication.name and
    hasRole("USER")
""")
    @PostAuthorize("""
    returnObject.body.username == authentication.name
""")
    ResponseEntity<GetUserDto> getUser(@PathVariable String username);

    @PutMapping("/users/{username}")
    @PreAuthorize("""
    #username == authentication.name and
    hasRole("USER")
""")
    @PostAuthorize("""
    returnObject.body.username == authentication.name
""")
    ResponseEntity<GetUserDto> updateUser(
            @PathVariable String username,
            @Valid @RequestBody UpdateUserDto userToUpdate
    );



}
