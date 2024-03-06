package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DisableUserUnitTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DisableUser underTest;

    @Test
    public void disableUser_ReturnsHTTP204NoContent_WithValidUsername(){
        String username = Mockito.anyString();
        when(userRepository.existsByUsername(username))
                .thenReturn(true);

        ResponseEntity<HttpStatus> result = underTest.disableUser(username);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void disableUser_ReturnsHTTP404NotFound_WithInvalidUsername(){
        String username = Mockito.anyString();
        when(userRepository.existsByUsername(username))
                .thenReturn(false);

        ResponseEntity<HttpStatus> result = underTest.disableUser(username);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}