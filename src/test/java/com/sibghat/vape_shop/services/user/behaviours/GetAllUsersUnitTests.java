package com.sibghat.vape_shop.services.user.behaviours;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllUsersUnitTests {

    @Mock
    private UserRepository userRepository;
    private final TestDataUtil testDataUtil = new TestDataUtil();

    @InjectMocks
    private GetAllUsers underTest;

    @Test
    public void getAllUsers_ReturnsHTTP200OK_WithNoUsernameToSearchAndValidData(){
        int page = 0;
        int size = 5;
        List<GetUserByAdminDto> getUserByAdminDtoList = testDataUtil.getUserByAdminDtoList();
        PageRequest newPageRequest = PageRequest.of(page,size);

        Page<GetUserByAdminDto> usersPage = new PageImpl<>(
                getUserByAdminDtoList,
                newPageRequest,
                getUserByAdminDtoList.size()
        );

        when(userRepository.getAllUsers("xyz",newPageRequest))
                .thenReturn(usersPage);

        var result = underTest.getAllUsers(
                page,
                size,
                "xyz",
                ""
        );

        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getTotalElements()).isEqualTo(getUserByAdminDtoList.size());
        assertThat(result.getBody().getTotalPages()).isEqualTo(1);
        assertThat(result.getBody().stream().toList()).isNotEmpty();
        assertThat(result.getBody().stream().toList()).isEqualTo(getUserByAdminDtoList);
    }

    @Test
    public void getAllUsers_ReturnsHTTP200OK_WithUsernameToSearchAndValidData(){
        int page = 0;
        int size = 5;
        List<GetUserByAdminDto> getUserByAdminDtoList = testDataUtil.getUserByAdminDtoList();
        List<GetUserByAdminDto> subList = getUserByAdminDtoList.stream()
                .filter(value -> value.getUsername().startsWith("aqrar"))
                .toList();
        PageRequest newPageRequest = PageRequest.of(page,size);

        Page<GetUserByAdminDto> usersPage = new PageImpl<>(
                subList,
                newPageRequest,
                getUserByAdminDtoList.size()
        );

        when(userRepository.getUsersBySearch("aqrar", "xyz",newPageRequest))
                .thenReturn(usersPage);

        var result = underTest.getAllUsers(
                page,
                size,
                "xyz",
                "aqrar"
        );

        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getTotalElements()).isEqualTo(2);
        assertThat(result.getBody().getTotalPages()).isEqualTo(1);
        assertThat(result.getBody().stream().toList()).isNotEmpty();
    }

}