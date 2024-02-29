package com.sibghat.vape_shop;

import com.sibghat.vape_shop.dtos.user.AddUserDto;

public class TestDataUtil {

    public AddUserDto addUserDto1(){
        return AddUserDto.builder()
                .firstName("aqrar")
                .lastName("Bazai")
                .username("aqrar")
                .password("aqrar")
                .contactNumber("123094123")
                .email("aqrar123@gmail.com")
                .enabled(true)
                .build();
    }

}
