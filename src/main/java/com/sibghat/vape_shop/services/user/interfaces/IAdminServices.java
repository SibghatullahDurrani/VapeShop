package com.sibghat.vape_shop.services.user.interfaces;

import com.sibghat.vape_shop.dtos.user.*;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.io.UnsupportedEncodingException;

public interface IAdminServices {

    ResponseEntity<GetUserDto> addUser(AddUserDto adminToAdd, String createdBy) throws MessagingException, UnsupportedEncodingException;
    ResponseEntity<GetUserDto> getUser(String username);
    ResponseEntity<GetUserDto> updateUser(String username, UpdateUserDto userToUpdate);
    ResponseEntity<HttpStatus> updatePassword(String username, UpdatePasswordDto updatePasswordDto);
    ResponseEntity<Page<GetUserByAdminDto>> getAllUsers(int page, int size, String role, String username);
    ResponseEntity<HttpStatus> disableUser(String username);

}
