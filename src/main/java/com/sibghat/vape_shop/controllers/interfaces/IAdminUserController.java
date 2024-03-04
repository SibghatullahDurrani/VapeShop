package com.sibghat.vape_shop.controllers.interfaces;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

public interface IAdminUserController {

    @PostMapping("/users/admins")
    @PreAuthorize("""
        hasRole("ADMIN")
    """)
    ResponseEntity<GetUserDto> addAdmin(@RequestBody AddUserDto adminToAdd, Authentication authentication);

    @GetMapping("/users/admins/{username}")
    @PreAuthorize("""
    hasRole("ADMIN") and
    #username == authentication.name
""")
    @PostAuthorize("""
    returnObject.body.username == authentication.name
""")
    ResponseEntity<GetUserByAdminDto> getAdmin(@PathVariable String username);

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

}
