package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdatePasswordDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IUserServices {

    ResponseEntity<GetUserDto> addUser(AddUserDto addUserDto);

    ResponseEntity<GetUserDto> getUser(String username);

    ResponseEntity<GetUserDto> updateUser(String username, UpdateUserDto userToUpdateDto);

    ResponseEntity<HttpStatus> verifyUser(String verificationCode);

    ResponseEntity<HttpStatus> updatePassword(String username, UpdatePasswordDto updatePasswordDto);
}
