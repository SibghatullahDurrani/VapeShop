package com.sibghat.vape_shop.services.user.behaviours.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IVerifyUserBehaviour {
    ResponseEntity<HttpStatus> verifyUser(String verification_code);
}
