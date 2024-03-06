package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.TestUtilMappers;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.mappers.user.AddUserDtoToUserMapper;
import com.sibghat.vape_shop.mappers.user.UserToGetUserDtoMapper;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.conditionEvaluators.IUserRelatedConditionEvaluators;
import com.sibghat.vape_shop.services.conditionEvaluators.UserRelatedConditionEvaluators;
import com.sibghat.vape_shop.services.user.behaviours.interfaces.ISendVerificationEmailBehaviour;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddClientUnitTests {

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
    @Mock
    private ISendVerificationEmailBehaviour sendVerificationEmailBehaviour;
    private final TestDataUtil testDataUtil = new TestDataUtil();
    private final TestUtilMappers testUtilMappers = new TestUtilMappers();

    @InjectMocks
    private AddClient underTest;

    @Test
    public void add_ReturnsHTTP201CreatedOkAndValidBody_WithValidData() throws MessagingException, UnsupportedEncodingException {
        AddUserDto userToAdd = testDataUtil.addUserDto1();
        User mappedUser = testUtilMappers.addUserDtoToUserMapper(userToAdd);
        GetUserDto mappedUserDto = testUtilMappers.userToGetUserDtoMapper(mappedUser);


        when(addUserDtoToUserMapper.mapFrom(userToAdd))
                .thenReturn(mappedUser);

        when(passwordEncoder.encode(mappedUser.getPassword()))
                .thenReturn(mappedUser.getPassword());


        when(userRepository.save(mappedUser))
                .thenReturn(mappedUser);

        when(userToGetUserDtoMapper.mapFrom(mappedUser))
                .thenReturn(mappedUserDto);


        var result = underTest.add(userToAdd,userToAdd.getUsername());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(mappedUserDto);
    }

}