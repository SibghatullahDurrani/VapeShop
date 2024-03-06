package com.sibghat.vape_shop.controllers.user;

import com.sibghat.vape_shop.controllers.user.interfaces.IAdminController;
import com.sibghat.vape_shop.dtos.user.*;
import com.sibghat.vape_shop.services.user.AdminServices;
import com.sibghat.vape_shop.services.user.interfaces.IAdminServices;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class AdminController implements IAdminController {

    private final IAdminServices adminServices;

    public AdminController(AdminServices adminServices) {
        this.adminServices = adminServices;
    }

    @Override
    public ResponseEntity<GetUserDto> addAdmin(@Valid @RequestBody AddUserDto adminToAdd, Authentication a) throws MessagingException, UnsupportedEncodingException {
        return adminServices.addUser(adminToAdd,a.getName());
    }


    @Override
    public ResponseEntity<GetUserDto> getAdmin(@PathVariable String username) {
        return adminServices.getUser(username);
    }

    @Override
    public ResponseEntity<Page<GetUserByAdminDto>> getAllAdmins(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String username
    ){
        String ADMIN_ROLE = "ROLE_ADMIN";
        return adminServices.getAllUsers(page, size, ADMIN_ROLE,username);
    }

    @Override
    public ResponseEntity<Page<GetUserByAdminDto>> getAllUsers(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String username
    ){
        String USER_ROLE = "ROLE_CLIENT";
        return adminServices.getAllUsers(page,size, USER_ROLE, username);
    }

    @Override
    public ResponseEntity<GetUserDto> updateAdmin(@PathVariable String username, @Valid @RequestBody UpdateUserDto updateUserDto) {
        return adminServices.updateUser(username, updateUserDto);
    }

    @Override
    public ResponseEntity<HttpStatus> updateAdminPassword(@PathVariable String username, @Valid @RequestBody UpdatePasswordDto updatePasswordDto){
        return adminServices.updatePassword(username,updatePasswordDto);
    }
    //TODO: add disable user endpoint

}
