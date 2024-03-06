package com.sibghat.vape_shop.services.user.interfaces;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdatePasswordDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.io.UnsupportedEncodingException;

public interface IClientServices {

    ResponseEntity<GetUserDto> addUser(AddUserDto addUserDto, String createdBy) throws MessagingException, UnsupportedEncodingException;

    ResponseEntity<GetUserDto> getUser(String username);

    ResponseEntity<GetUserDto> updateUser(String username, UpdateUserDto userToUpdateDto);

    ResponseEntity<HttpStatus> verifyUser(String verificationCode);

    ResponseEntity<HttpStatus> updatePassword(String username, UpdatePasswordDto updatePasswordDto);
    ResponseEntity<HttpStatus> disableUser(String username);
}
