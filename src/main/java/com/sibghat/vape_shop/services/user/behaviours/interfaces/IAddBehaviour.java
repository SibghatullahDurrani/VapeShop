package com.sibghat.vape_shop.services.user.behaviours.interfaces;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.io.UnsupportedEncodingException;

public interface IAddBehaviour {

    ResponseEntity<GetUserDto> add(AddUserDto userToAdd, String createdBy) throws MessagingException, UnsupportedEncodingException;

}
