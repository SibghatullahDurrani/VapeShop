package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import org.springframework.http.ResponseEntity;

public interface IAdminUserServices {

    AddUserDto addAdmin(AddUserDto adminToAdd,String createdBy);


    ResponseEntity<GetUserDto> getAdmin(String username);
}
