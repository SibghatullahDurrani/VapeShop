package com.sibghat.vape_shop.services.user.behaviours.interfaces;

import com.sibghat.vape_shop.dtos.user.GetUserDto;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface ISendVerificationEmailBehaviour {

    void sendVerificationEmail(GetUserDto getUserDto,String URL) throws MessagingException, UnsupportedEncodingException;
}
