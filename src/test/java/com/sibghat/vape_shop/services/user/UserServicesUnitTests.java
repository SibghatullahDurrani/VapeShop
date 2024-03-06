package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.TestUtilMappers;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.*;
import com.sibghat.vape_shop.mappers.user.AddUserDtoToUserMapper;
import com.sibghat.vape_shop.mappers.user.UserToGetUserDtoMapper;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.conditionEvaluators.UserRelatedConditionEvaluators;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServicesUnitTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRelatedConditionEvaluators userRelatedConditionEvaluators;

    @Mock
    private AddUserDtoToUserMapper addUserDtoToUserMapper;

    @Mock
    private UserToGetUserDtoMapper userToGetUserDtoMapper;


    @InjectMocks
    private UserServices underTest;

    private final TestDataUtil testDataUtil = new TestDataUtil();
    private final TestUtilMappers testUtilMappers = new TestUtilMappers();


    @Test
    public void addUser_ReturnsHTTP200OKAndValidBody_WithValidData(){
        AddUserDto user = testDataUtil.addUserDto1();
        User userResult = User.builder()
                .id(1L)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .contactNumber(user.getContactNumber())
                .email(user.getEmail())
                .build();

        doReturn(userResult)
                .when(addUserDtoToUserMapper).mapFrom(Mockito.any(AddUserDto.class));

        when(passwordEncoder.encode(Mockito.anyString()))
                .thenReturn(user.getPassword());

        doReturn(testUtilMappers.userToGetUserDtoMapper(userResult))
                .when(userToGetUserDtoMapper).mapFrom(Mockito.any(User.class));

        doReturn(userResult)
                .when(userRepository).save(Mockito.any(User.class));


        ResponseEntity<GetUserDto> result = underTest.addUser(user);


        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(testUtilMappers.addUserDtoToGetUserDtoMapper(user));

    }

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

    @Test
    public void getUser_ReturnsHTTP200OkAndUser_WithValidUsername(){
        GetUserDto user = testDataUtil.getUserDto();
        String username = user.getUsername();

        when(userRepository.getUserByUsername(username))
                .thenReturn(Optional.of(user));

        ResponseEntity<GetUserDto> result = underTest.getUser(username);

        assertThat(result.getBody()).isEqualTo(user);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getUser_ReturnsHTTP404NotFound_WithInValidUsername(){
        GetUserDto user = testDataUtil.getUserDto();
        String username = user.getUsername();

        when(userRepository.getUserByUsername(username))
                .thenReturn(Optional.empty());

        ResponseEntity<GetUserDto> result = underTest.getUser(username);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

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
