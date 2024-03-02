package com.sibghat.vape_shop;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;

import java.time.LocalDateTime;

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

    public GetUserByAdminDto userToGetUserByAdminDto(User user){
        user.setCreatedAt(user.getCreatedAt().minusNanos(user.getCreatedAt().getNano()));
        return GetUserByAdminDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .contactNumber(user.getContactNumber())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .lastModifiedAt(user.getLastModifiedAt())
                .createdBy(user.getCreatedBy())
                .lastModifiedBy(user.getLastModifiedBy())
                .build();
    }
}
