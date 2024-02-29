package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetAdminDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface IAdminUserServices {

    AddUserDto addAdmin(AddUserDto adminToAdd,String createdBy);


    ResponseEntity<GetAdminDto> getAdmin(String username);

    ResponseEntity<Page<GetAdminDto>> getAllUsers(int page, int size, String role);
}
