package com.sibghat.vape_shop;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;

public class UtilMappers {

    public GetUserDto addUserDtoToGetUserDtoMapper (AddUserDto addUserDto){
        return GetUserDto.builder()
                .username(addUserDto.getUsername())
                .firstName(addUserDto.getFirstName())
                .lastName(addUserDto.getLastName())
                .email(addUserDto.getEmail())
                .contactNumber(addUserDto.getContactNumber())
                .build();
    }

    public GetUserDto userToGetUserDtoMapper(User user){
        return GetUserDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .contactNumber(user.getContactNumber())
                .verificationCode(user.getVerificationCode())
                .build();
    }
}
