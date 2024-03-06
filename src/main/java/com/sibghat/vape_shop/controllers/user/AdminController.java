package com.sibghat.vape_shop.controllers.user;

import com.sibghat.vape_shop.controllers.user.interfaces.IAdminController;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.services.user.AdminServices;
import com.sibghat.vape_shop.services.user.interfaces.IAdminServices;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController implements IAdminController {

    private final IAdminServices adminUserServices;

    public AdminController(AdminServices adminServices) {
        this.adminUserServices = adminServices;
    }

    @Override
    public ResponseEntity<GetUserDto> addAdmin(@Valid @RequestBody AddUserDto adminToAdd, Authentication a){
        return adminUserServices.addUser(adminToAdd,a.getName());
    }


    @Override
    public ResponseEntity<GetUserDto> getAdmin(@PathVariable String username) {
        return adminUserServices.getUser(username);
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
        String USER_ROLE = "ROLE_CLIENT";
        return adminUserServices.getAllUsers(page,size, USER_ROLE, username);
    }


}
