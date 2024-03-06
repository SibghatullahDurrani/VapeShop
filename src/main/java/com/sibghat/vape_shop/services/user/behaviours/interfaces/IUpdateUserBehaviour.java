package com.sibghat.vape_shop.services.user.behaviours.interfaces;

import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface IUpdateUserBehaviour {

    ResponseEntity<GetUserDto> updateUser(String username, UpdateUserDto userToUpdateDto);

}
