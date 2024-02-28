package com.sibghat.vape_shop.controllers;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.services.user.AdminUserServices;
import com.sibghat.vape_shop.services.user.IAdminUserServices;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUserController {

    private final IAdminUserServices adminUserServices;

    public AdminUserController(AdminUserServices adminUserServices) {
        this.adminUserServices = adminUserServices;
    }

    @PostMapping("/users/admins")
    @PreAuthorize("""
        hasRole("ADMIN")
    """)
    public AddUserDto addAdmin(@RequestBody AddUserDto adminToAdd, Authentication authentication){
        return adminUserServices.addAdmin(adminToAdd,authentication.getName());
    }

}
