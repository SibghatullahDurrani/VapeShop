package com.sibghat.vape_shop.controllers;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.services.user.AdminUserServices;
import com.sibghat.vape_shop.services.user.IAdminUserServices;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminUserController {

    private final IAdminUserServices adminUserServices;
    private final String USER_ROLE = "ROLE_USER";
    private final String ADMIN_ROLE = "ROLE_ADMIN";

    public AdminUserController(AdminUserServices adminUserServices) {
        this.adminUserServices = adminUserServices;
    }

    @PostMapping("/users/admins")
    @PreAuthorize("""
        hasRole("ADMIN")
    """)
    public ResponseEntity<GetUserDto> addAdmin(@RequestBody AddUserDto adminToAdd, Authentication authentication){
        return adminUserServices.addAdmin(adminToAdd,authentication.getName());
    }

    @GetMapping("/users/admins/{username}")
    @PreAuthorize("""
    hasRole("ADMIN") and
    #username == authentication.name
""")
    @PostAuthorize("""
    returnObject.body.username == authentication.name
""")
    public ResponseEntity<GetUserByAdminDto> getAdmin(@PathVariable String username) {
        return adminUserServices.getAdmin(username);
    }

    @GetMapping("/users/admins")
    @PreAuthorize("""
    hasRole("ADMIN")
""")
    public ResponseEntity<Page<GetUserByAdminDto>> getAllAdmins(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam Optional<String> username
    ){
        return adminUserServices.getAllUsers(page, size, ADMIN_ROLE,username);
    }

    @GetMapping("/users")
    @PreAuthorize(
            """
    hasRole("ADMIN")
"""
    )
    public ResponseEntity<Page<GetUserByAdminDto>> getAllUsers(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam Optional<String> username
    ){
        return adminUserServices.getAllUsers(page,size, USER_ROLE , username);
    }


}
