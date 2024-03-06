package com.sibghat.vape_shop.services.user.behaviours.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IDisableUserBehaviour {


    ResponseEntity<HttpStatus> disableUser(String username);

}
