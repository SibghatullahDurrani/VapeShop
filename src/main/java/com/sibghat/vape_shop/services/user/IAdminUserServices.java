package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IAdminUserServices {

    AddUserDto addAdmin(AddUserDto adminToAdd,String createdBy);


    ResponseEntity<GetUserByAdminDto> getAdmin(String username);

    ResponseEntity<Page<GetUserByAdminDto>> getAllUsers(int page, int size, String role, Optional<String> username);

}
