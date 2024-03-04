package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface IAdminUserServices {

    ResponseEntity<GetUserDto> addAdmin(AddUserDto adminToAdd, String createdBy);


    ResponseEntity<GetUserByAdminDto> getAdmin(String username);

    ResponseEntity<Page<GetUserByAdminDto>> getAllUsers(int page, int size, String role, String username);

}
