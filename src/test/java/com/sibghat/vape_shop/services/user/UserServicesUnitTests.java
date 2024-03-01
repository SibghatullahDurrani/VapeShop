package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.dtos.user.VerifyUserDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServicesUnitTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServices userServices;

    private final TestDataUtil testDataUtil = new TestDataUtil();




    @Test
    public void verifyUser_ReturnsHTTP404NotFound_WithIncorrectVerificationCode(){
        when(userRepository.getUserByVerificationCode(Mockito.anyString()))
                .thenReturn(Optional.empty());

        ResponseEntity<HttpStatus> result = userServices.verifyUser("xyz");

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void verifyUser_ReturnsHTTP200OK_WithNonExpiredVerificationCode(){
        VerifyUserDto user = testDataUtil.userWithInvalidTokenExpiration();
        when(userRepository.getUserByVerificationCode(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(user));

        ResponseEntity<HttpStatus> result = userServices.verifyUser("xyz");

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void verifyUser_ReturnsHTTP404NotFound_WithExpiredVerificationCode(){
        VerifyUserDto user = testDataUtil.userWithValidTokenExpiration();

        when(userRepository.getUserByVerificationCode(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(user));

        ResponseEntity<HttpStatus> result = userServices.verifyUser("xyz");

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }



}
