package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.dtos.user.UpdatePasswordDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdatePasswordUnitTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UpdatePassword underTest;


    @Test
    public void updatePassword_ReturnsHTTP200OK_WithValidUsernameAndPassword(){
        String username = "xyz";
        String password = "abc";

        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .previousPassword("abc")
                .newPassword("asdf")
                .build();

        when(userRepository.getPassword(username))
                .thenReturn(Optional.of(password));

        when(passwordEncoder.matches(updatePasswordDto.getPreviousPassword(), password))
                .thenReturn(true);

        ResponseEntity<HttpStatus> result = underTest.updatePassword(username,updatePasswordDto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void updatePassword_ReturnsHTTP400BadRequest_WithInvalidPreviousPassword(){
        String username = "xyz";
        String password = "abc";

        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .previousPassword(Mockito.anyString())
                .newPassword("asdf")
                .build();

        when(userRepository.getPassword(username))
                .thenReturn(Optional.of(password));

        when(passwordEncoder.matches(updatePasswordDto.getPreviousPassword(), password))
                .thenReturn(false);

        ResponseEntity<HttpStatus> result = underTest.updatePassword(username,updatePasswordDto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void updatePassword_ReturnsHTTP404NotFound_WithInvalidUsername(){
        String username = "xyz";

        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .previousPassword(Mockito.anyString())
                .newPassword("asdf")
                .build();

        when(userRepository.getPassword(username))
                .thenReturn(Optional.empty());

        ResponseEntity<HttpStatus> result = underTest.updatePassword(username,updatePasswordDto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }
}