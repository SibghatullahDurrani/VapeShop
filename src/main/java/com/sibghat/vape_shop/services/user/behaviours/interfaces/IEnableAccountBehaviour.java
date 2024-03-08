package com.sibghat.vape_shop.services.user.behaviours.interfaces;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;

public interface IEnableAccountBehaviour {

    ResponseEntity<HttpStatus> enableAccount(String username) throws MessagingException, UnsupportedEncodingException;

}
