package com.sibghat.vape_shop;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.VerifyUserDto;

import java.time.Instant;

public class TestDataUtil {


    public User validUser1(){
        return User.builder()
                .firstName("xyz")
                .lastName("xyz")
                .username("sibghat")
                .email("xyz@gmail.com")
                .password("xyz")
                .contactNumber("12345678")
                .verificationCode("xyz")
                .createdBy("xyz")
                .build();
    }

    public VerifyUserDto userWithValidTokenExpiration(){
        return VerifyUserDto.builder()
                .verificationCodeValidTill(Instant.now().getEpochSecond() - 500)
                .username("xyz")
                .build();
    }

    public VerifyUserDto userWithInvalidTokenExpiration(){
        return VerifyUserDto.builder()
                .verificationCodeValidTill(Instant.now().getEpochSecond() + 500)
                .username("xyz")
                .build();

    }

    public AddUserDto addUserDto1(){
        return AddUserDto.builder()
                .firstName("aqrar")
                .lastName("Bazai")
                .username("aqrar")
                .password("aqrar")
                .contactNumber("123094123")
                .email("aqrar123@gmail.com")
                .build();
    }

}
