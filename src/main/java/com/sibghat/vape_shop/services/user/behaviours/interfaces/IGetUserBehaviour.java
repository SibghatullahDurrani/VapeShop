package com.sibghat.vape_shop.services.user.behaviours.interfaces;

import com.sibghat.vape_shop.dtos.user.GetUserDto;
import org.springframework.http.ResponseEntity;

public interface IGetUserBehaviour {

    ResponseEntity<GetUserDto> getUser(String username);
}
