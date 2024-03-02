package com.sibghat.vape_shop;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
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
                .createdBy("sibghat")
                .build();
    }

    public User validUser2(){
        return User.builder()
                .firstName("xyz")
                .lastName("xyz")
                .username("sibghat2")
                .email("xyz2@gmail.com")
                .password("xyz2")
                .contactNumber("12345678123")
                .verificationCode("xyz2")
                .createdBy("sibghat2")
                .build();
    }

    public User validUser3(){
        return User.builder()
                .firstName("xyz")
                .lastName("xyz")
                .username("sibghat3")
                .email("xyz3@gmail.com")
                .password("xyz3")
                .contactNumber("12123345678123")
                .verificationCode("xyz3")
                .createdBy("sibghat3")
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

    public UpdateUserDto updateUserDto(){
        return UpdateUserDto.builder()
                .firstName("aqrar")
                .lastName("bazai")
                .contactNumber("12345678")
                .build();
    }

    public GetUserDto getUserDto(){
        return GetUserDto.builder()
                .username("sibghat")
                .firstName("xyz")
                .lastName("xyz")
                .email("xyz@123.com")
                .contactNumber("12345678")
                .verificationCode("xyzasd")
                .build();
    }

}
