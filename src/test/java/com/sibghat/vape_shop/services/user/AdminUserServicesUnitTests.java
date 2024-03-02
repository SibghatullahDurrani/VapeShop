package com.sibghat.vape_shop.services.user;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.TestUtilMappers;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.mappers.user.AddUserDtoToUserMapper;
import com.sibghat.vape_shop.mappers.user.UserToGetUserDtoMapper;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.conditionEvaluators.UserRelatedConditionEvaluators;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminUserServicesUnitTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AddUserDtoToUserMapper addUserDtoToUserMapper;
    @Mock
    private UserToGetUserDtoMapper userToGetUserDtoMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRelatedConditionEvaluators userRelatedConditionEvaluators;
    private final TestDataUtil testDataUtil = new TestDataUtil();
    private final TestUtilMappers testUtilMappers = new TestUtilMappers();
    @InjectMocks
    private AdminUserServices underTest;

    @Test
    public void addAdmin_ReturnsHTTP200OkAndValidBody_WithValidData(){
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        String createdBy = userToAdd.getUsername();
        User mappedUser = testUtilMappers.addUserDtoToUserMapper(userToAdd);
        GetUserDto mappedUserDto = testUtilMappers.userToGetUserDtoMapper(mappedUser);


        when(addUserDtoToUserMapper.mapFrom(userToAdd))
                .thenReturn(mappedUser);

        when(passwordEncoder.encode(mappedUser.getPassword()))
                .thenReturn(mappedUser.getPassword());

        mappedUser.setCreatedBy(createdBy);
        mappedUser.setRole("ROLE_ADMIN");

        when(userRepository.save(mappedUser))
                .thenReturn(mappedUser);

        when(userToGetUserDtoMapper.mapFrom(mappedUser))
                .thenReturn(mappedUserDto);

        var result = underTest.addAdmin(userToAdd,createdBy);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(mappedUserDto);

    }

    @Test
    public void getAdmin_ReturnsHTTP200OKAndValidBody_WithValidUsername(){
        GetUserByAdminDto adminDto = testDataUtil.getUserByAdminDto();
        String username = adminDto.getUsername();

        when(userRepository.getAdminByUsername(username))
                .thenReturn(Optional.of(adminDto));

        var result = underTest.getAdmin(username);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(adminDto);

    }

}
