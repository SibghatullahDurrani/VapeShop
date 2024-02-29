package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import org.springframework.http.ResponseEntity;

public interface IUserServices {

    ResponseEntity<AddUserDto> addUser(AddUserDto addUserDto);

    ResponseEntity<GetUserDto> getUser(String username);

    ResponseEntity<GetUserDto> updateUser(String username, UpdateUserDto userToUpdateDto);
}
