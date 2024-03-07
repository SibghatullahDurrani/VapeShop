package com.sibghat.vape_shop.controllers.user.interfaces;

import com.sibghat.vape_shop.dtos.user.*;
import com.sibghat.vape_shop.services.user.behaviours.UpdateUser;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

public interface IAdminController {

    @PostMapping("/users/admins")
    @PreAuthorize("""
        hasRole("ADMIN")
    """)
    ResponseEntity<GetUserDto> addAdmin(@Valid @RequestBody AddUserDto adminToAdd, Authentication authentication) throws MessagingException, UnsupportedEncodingException;

    @GetMapping("/users/admins/{username}")
    @PreAuthorize("""
    hasRole("ADMIN") and
    #username == authentication.name
""")
    @PostAuthorize("""
    returnObject.body.username == authentication.name
""")
    ResponseEntity<GetUserDto> getAdmin(@PathVariable String username);

    @GetMapping("/users/admins")
    @PreAuthorize("""
    hasRole("ADMIN")
""")
    ResponseEntity<Page<GetUserByAdminDto>> getAllAdmins(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String username
    );

    @GetMapping("/users")
    @PreAuthorize(
            """
    hasRole("ADMIN")
"""
    )
    ResponseEntity<Page<GetUserByAdminDto>> getAllUsers(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String username
    );

    @PutMapping("/users/admins/{username}")
    @PreAuthorize("""
    hasRole("ADMIN") and
    #username == authentication.name
""")
    ResponseEntity<GetUserDto> updateAdmin(@PathVariable String username, @Valid @RequestBody UpdateUserDto updateUserDto);

    @PatchMapping("/users/admins/{username}")
    @PreAuthorize("""
    hasRole("ADMIN") and
    #username == authentication.name
""")
    ResponseEntity<HttpStatus> updateAdminPassword(@PathVariable String username, @Valid @RequestBody UpdatePasswordDto updatePasswordDto);

    @DeleteMapping("/users/admins/{username}")
    @PreAuthorize("""
    hasRole("ADMIN") and
    #username == authentication.name
""")
    ResponseEntity<HttpStatus> disableAdmin(@PathVariable String username);
}
