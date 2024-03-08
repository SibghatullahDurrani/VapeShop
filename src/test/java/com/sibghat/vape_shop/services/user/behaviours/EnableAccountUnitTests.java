package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.ISendVerificationEmailBehaviour;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnableAccountUnitTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SendVerificationEmail sendVerificationEmail;

    private final TestDataUtil testDataUtil = new TestDataUtil();

    @InjectMocks
    private EnableAccount underTest;

    @Test
    void enableAccount_ReturnsHTTP200OK_WithCorrectUsername() throws MessagingException, UnsupportedEncodingException {
        String username = "xyz";
        GetUserDto user = testDataUtil.getUserDto();

        when(userRepository.existsByUsername(username))
                .thenReturn(true);

        when(userRepository.getUserByUsername(username))
                .thenReturn(Optional.ofNullable(user));

        ResponseEntity<HttpStatus> result = underTest.enableAccount(username);



        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void enableAccount_ReturnsHTTP404NotFound_WithIncorrectUsername() throws MessagingException, UnsupportedEncodingException {
        String username = "xyz";

        when(userRepository.existsByUsername(username))
                .thenReturn(false);

        ResponseEntity<HttpStatus> result = underTest.enableAccount(username);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }


}