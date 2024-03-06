package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.dtos.user.VerifyUserDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerifyUserUnitTests {

    @Mock
    private UserRepository userRepository;
    private final TestDataUtil testDataUtil = new TestDataUtil();
    @InjectMocks
    private VerifyUser underTest;

    @Test
    public void verifyUser_ReturnsHTTP404NotFound_WithIncorrectVerificationCode(){
        when(userRepository.getUserByVerificationCode(Mockito.anyString()))
                .thenReturn(Optional.empty());

        ResponseEntity<HttpStatus> result = underTest.verifyUser("xyz");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void verifyUser_ReturnsHTTP200OK_WithNonExpiredVerificationCode(){
        VerifyUserDto user = testDataUtil.userWithInvalidTokenExpiration();
        when(userRepository.getUserByVerificationCode(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(user));

        ResponseEntity<HttpStatus> result = underTest.verifyUser("xyz");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void verifyUser_ReturnsHTTP404NotFound_WithExpiredVerificationCode(){
        VerifyUserDto user = testDataUtil.userWithValidTokenExpiration();

        when(userRepository.getUserByVerificationCode(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(user));

        ResponseEntity<HttpStatus> result = underTest.verifyUser("xyz");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}