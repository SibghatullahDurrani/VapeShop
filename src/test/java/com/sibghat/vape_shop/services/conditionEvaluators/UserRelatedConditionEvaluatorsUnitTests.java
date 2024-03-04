package com.sibghat.vape_shop.services.conditionEvaluators;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserRelatedConditionEvaluatorsUnitTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRelatedConditionEvaluators underTest;
    private final TestDataUtil testDataUtil = new TestDataUtil();

    @Test
    public void checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser_ThrowsException_WithUsernameThatAlreadyExists(){

        AddUserDto userToAdd = testDataUtil.addUserDto1();

        doReturn(false)
                .when(userRepository).existsByContactNumber(Mockito.anyString());
        doReturn(false)
                .when(userRepository).existsByEmail(Mockito.anyString());
        doReturn(true)
                .when(userRepository).existsByUsername(Mockito.anyString());

        Assertions.assertThrows(
                EntityExistsException.class,
                () -> underTest
                        .checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser(userToAdd),
                "username "
        );

    }

    @Test
    public void checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser_ThrowsException_WithEmailThatAlreadyExists(){

        AddUserDto userToAdd = testDataUtil.addUserDto1();

        doReturn(false)
                .when(userRepository).existsByContactNumber(Mockito.anyString());
        doReturn(true)
                .when(userRepository).existsByEmail(Mockito.anyString());
        doReturn(false)
                .when(userRepository).existsByUsername(Mockito.anyString());

        Assertions.assertThrows(
                EntityExistsException.class,
                () -> underTest
                        .checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser(userToAdd),
                "email "
        );

    }

    @Test
    public void checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser_ThrowsException_WithContactNumberThatAlreadyExists(){

        AddUserDto userToAdd = testDataUtil.addUserDto1();

        doReturn(true)
                .when(userRepository).existsByContactNumber(Mockito.anyString());
        doReturn(false)
                .when(userRepository).existsByEmail(Mockito.anyString());
        doReturn(false)
                .when(userRepository).existsByUsername(Mockito.anyString());

        Assertions.assertThrows(
                EntityExistsException.class,
                () -> underTest
                        .checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser(userToAdd),
                "contactNumber "
        );

    }

    @Test
    public void checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser_ThrowsException_WithEveryFieldThatAlreadyExists(){

        AddUserDto userToAdd = testDataUtil.addUserDto1();

        doReturn(true)
                .when(userRepository).existsByContactNumber(Mockito.anyString());
        doReturn(true)
                .when(userRepository).existsByEmail(Mockito.anyString());
        doReturn(true)
                .when(userRepository).existsByUsername(Mockito.anyString());

        Assertions.assertThrows(
                EntityExistsException.class,
                () -> underTest
                        .checkThatUserDoesNotAlreadyExistsBeforeAddingANewUser(userToAdd),
                "contactNumber email username "
        );

    }

    @Test
    public void checkThatContactNumberDoesNotAlreadyExistsBeforeAddingANewUser_ThrowsException_WithContactNumberThatAlreadyExists() {
        doReturn(true)
                .when(userRepository).existsByContactNumber(Mockito.anyString());

        Assertions.assertThrows(
                EntityExistsException.class,
                () -> underTest
                        .checkThatContactNumberDoesNotAlreadyExistsBeforeUpdatingUser(Mockito.anyString()),
                "contactNumber"
        );
    }

}
