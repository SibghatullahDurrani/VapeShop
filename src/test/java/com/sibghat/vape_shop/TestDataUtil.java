package com.sibghat.vape_shop;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {


    public User validUser1(){
        return User.builder()
                .firstName("xyz")
                .lastName("xyz")
                .username("aqrar")
                .email("xyz@gmail.com")
                .password("xyz")
                .contactNumber("12345678")
                .verificationCode("xyz")
                .verificationCodeValidTill(Instant.now().getEpochSecond() + 600L)
                .createdBy("aqrar")
                .build();
    }

    public User validUser2(){
        return User.builder()
                .firstName("xyz")
                .lastName("xyz")
                .username("sibghat")
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
                .username("sami")
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

    public AddUserDto addUserDto2(){
        return AddUserDto.builder()
                .firstName("sibghat")
                .lastName("durrani")
                .username("sibghat")
                .password("aqrar")
                .contactNumber("1230941231")
                .email("sibghat@gmail.com")
                .build();
    }

    public AddUserDto addUserDto3(){
        return AddUserDto.builder()
                .firstName("sami")
                .lastName("durrani")
                .username("sami")
                .password("aqrar")
                .contactNumber("1230941232")
                .email("sami@gmail.com")
                .build();
    }

    public UpdateUserDto updateUserDto(){
        return UpdateUserDto.builder()
                .firstName("aqrar updated")
                .lastName("bazai updated")
                .contactNumber("12345678 updated")
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

    public GetUserByAdminDto getUserByAdminDto(){
        return GetUserByAdminDto.builder()
                .username("sibghat")
                .firstName("sibghat")
                .lastName("durrani")
                .email("xyz@gmail.com")
                .contactNumber("12345678")
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .createdBy("sibghat")
                .lastModifiedBy("sibghat")
                .build();

    }

    public GetUserByAdminDto getUserByAdminDto2(){
        return GetUserByAdminDto.builder()
                .username("sibghat2")
                .firstName("sibghat")
                .lastName("durrani")
                .email("xyz@gmail.com")
                .contactNumber("12345678")
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .createdBy("sibghat")
                .lastModifiedBy("sibghat")
                .build();

    }

    public GetUserByAdminDto getUserByAdminDto3(){
        return GetUserByAdminDto.builder()
                .username("aqrar")
                .firstName("sibghat")
                .lastName("durrani")
                .email("xyz@gmail.com")
                .contactNumber("12345678")
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .createdBy("sibghat")
                .lastModifiedBy("sibghat")
                .build();
    }

    public GetUserByAdminDto getUserByAdminDto4(){
        return GetUserByAdminDto.builder()
                .username("aqrar2")
                .firstName("sibghat")
                .lastName("durrani")
                .email("xyz@gmail.com")
                .contactNumber("12345678")
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .createdBy("sibghat")
                .lastModifiedBy("sibghat")
                .build();
    }

    public List<GetUserByAdminDto> getUserByAdminDtoList(){
        List<GetUserByAdminDto> getUserByAdminDtoList = new ArrayList<>();

        getUserByAdminDtoList.add(getUserByAdminDto());
        getUserByAdminDtoList.add(getUserByAdminDto2());
        getUserByAdminDtoList.add(getUserByAdminDto3());
        getUserByAdminDtoList.add(getUserByAdminDto4());

        return getUserByAdminDtoList;
    }

}
