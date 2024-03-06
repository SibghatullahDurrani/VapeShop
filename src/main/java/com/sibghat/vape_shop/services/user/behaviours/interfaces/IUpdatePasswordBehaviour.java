package com.sibghat.vape_shop.services.user.behaviours.interfaces;

import com.sibghat.vape_shop.dtos.user.UpdatePasswordDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IUpdatePasswordBehaviour {
    ResponseEntity<HttpStatus> updatePassword(String username, UpdatePasswordDto updatePasswordDto);
}
