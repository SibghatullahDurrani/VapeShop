package com.sibghat.vape_shop.controllers.user.interfaces;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdatePasswordDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

public interface IClientController {

    @PostMapping("/users")
    ResponseEntity<GetUserDto> addUser(@Valid @RequestBody AddUserDto userToAdd);

    @PatchMapping("/users/verify/{verification_code}")
    ResponseEntity<HttpStatus> verifyUser(@PathVariable String verification_code);

    @GetMapping("/users/{username}")
    @PreAuthorize("""
    #username == authentication.name and
    hasRole("CLIENT")
""")
    @PostAuthorize("""
    returnObject.body.username == authentication.name
""")
    ResponseEntity<GetUserDto> getUser(@PathVariable String username);

    @PutMapping("/users/{username}")
    @PreAuthorize("""
    #username == authentication.name and
    hasRole("CLIENT")
""")
    @PostAuthorize("""
    returnObject.body.username == authentication.name
""")
    ResponseEntity<GetUserDto> updateUser(
            @PathVariable String username,
            @Valid @RequestBody UpdateUserDto userToUpdate
    );

    @PatchMapping("/users/{username}")
    @PreAuthorize("""
    #username == authentication.name and
    hasRole("CLIENT")
""")
    ResponseEntity<HttpStatus> updatePassword(
      @PathVariable String username,
      @Valid @RequestBody UpdatePasswordDto updatePasswordDto
    );



}
