package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.TestUtilMappers;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserUnitTests {

    @Mock
    private UserRepository userRepository;
    private final TestDataUtil testDataUtil = new TestDataUtil();

    @InjectMocks
    private GetUser underTest;

    @Test
    public void getUser_ReturnsHTTP200OKAndValidBody_WithValidUsername(){
        GetUserDto adminDto = testDataUtil.getUserDto();
        String username = adminDto.getUsername();

        when(userRepository.getUserByUsername(username))
                .thenReturn(Optional.of(adminDto));

        var result = underTest.getUser(username);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(adminDto);
    }

    @Test
    public void getAdmin_ReturnsHTTP404NotFound_WithInvalidUsername(){
        when(userRepository.getUserByUsername(Mockito.anyString()))
                .thenReturn(Optional.empty());

        var result = underTest.getUser(Mockito.anyString());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}