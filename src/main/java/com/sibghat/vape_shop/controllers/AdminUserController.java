package com.sibghat.vape_shop.controllers;

import com.sibghat.vape_shop.controllers.interfaces.IAdminUserController;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.services.user.AdminUserServices;
import com.sibghat.vape_shop.services.user.IAdminUserServices;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUserController implements IAdminUserController {

    private final IAdminUserServices adminUserServices;

    public AdminUserController(AdminUserServices adminUserServices) {
        this.adminUserServices = adminUserServices;
    }

    @Override
    public ResponseEntity<GetUserDto> addAdmin(@RequestBody AddUserDto adminToAdd, Authentication authentication){
        return adminUserServices.addAdmin(adminToAdd,authentication.getName());
    }


    @Override
    public ResponseEntity<GetUserByAdminDto> getAdmin(@PathVariable String username) {
        return adminUserServices.getAdmin(username);
    }

    @Override
    public ResponseEntity<Page<GetUserByAdminDto>> getAllAdmins(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String username
    ){
        String ADMIN_ROLE = "ROLE_ADMIN";
        return adminUserServices.getAllUsers(page, size, ADMIN_ROLE,username);
    }

    @Override
    public ResponseEntity<Page<GetUserByAdminDto>> getAllUsers(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String username
    ){
        String USER_ROLE = "ROLE_USER";
        return adminUserServices.getAllUsers(page,size, USER_ROLE, username);
    }


}
