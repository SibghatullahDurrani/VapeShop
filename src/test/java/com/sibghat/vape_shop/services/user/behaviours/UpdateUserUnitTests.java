package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.TestUtilMappers;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.UpdateUserDto;
import com.sibghat.vape_shop.mappers.user.UserToGetUserDtoMapper;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.conditionEvaluators.UserRelatedConditionEvaluators;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserUnitTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRelatedConditionEvaluators userRelatedConditionEvaluators;
    @Mock
    private UserToGetUserDtoMapper userToGetUserDtoMapper;

    private final TestDataUtil testDataUtil = new TestDataUtil();
    private final TestUtilMappers testUtilMappers = new TestUtilMappers();

    @InjectMocks
    private UpdateUser underTest;

    @Test
    public void updateUser_ReturnsHTTP200OkAndUpdatedUser_WithValidData(){

        String username = "sibghat";
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .firstName("sibghat")
                .lastName("Durrani")
                .contactNumber("87654321")
                .build();
        User updatedUser = testDataUtil.validUser1();

        when(userRepository.findUserByUsername(username))
                .thenReturn(Optional.ofNullable(updatedUser));

        assert updatedUser != null;
        updatedUser.setFirstName(updateUserDto.getFirstName());
        updatedUser.setLastName(updateUserDto.getLastName());
        updatedUser.setContactNumber(updateUserDto.getContactNumber());
        updatedUser.setLastModifiedAt(LocalDateTime.now());
        updatedUser.setLastModifiedBy(username);

        when(userRepository.save(updatedUser))
                .thenReturn(updatedUser);

        when(userToGetUserDtoMapper.mapFrom(updatedUser))
                .thenReturn(testUtilMappers.userToGetUserDtoMapper(updatedUser));


        ResponseEntity<GetUserDto> result = underTest.updateUser(username,updateUserDto);

        assertThat(result.getBody()).isEqualTo(testUtilMappers.userToGetUserDtoMapper(updatedUser));
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void updateUser_ReturnsHTTP404NotFound_WithInvalidUsername(){
        String username = "sibghat";
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .firstName("sibghat")
                .lastName("Durrani")
                .contactNumber("87654321")
                .build();

        when(userRepository.findUserByUsername(username))
                .thenReturn(Optional.empty());


        ResponseEntity<GetUserDto> result = underTest.updateUser(username,updateUserDto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}