package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.TestUtilMappers;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.mappers.user.AddUserDtoToUserMapper;
import com.sibghat.vape_shop.mappers.user.UserToGetUserDtoMapper;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.conditionEvaluators.IUserRelatedConditionEvaluators;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddAdminUnitTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AddUserDtoToUserMapper addUserDtoToUserMapper;
    @Mock
    private UserToGetUserDtoMapper userToGetUserDtoMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private IUserRelatedConditionEvaluators userRelatedConditionEvaluators;
    private final TestDataUtil testDataUtil = new TestDataUtil();
    private final TestUtilMappers testUtilMappers = new TestUtilMappers();

    @InjectMocks
    private AddAdmin underTest;

    @Test
    public void addAdmin_ReturnsHTTP201CreatedOkAndValidBody_WithValidData(){
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


        var result = underTest.add(userToAdd,userToAdd.getUsername());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(mappedUserDto);
    }


}