package com.sibghat.vape_shop.repositories;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.TestUtilMappers;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.dtos.user.VerifyUserDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryUnitTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    private final TestUtilMappers testUtilMappers = new TestUtilMappers();
    private final TestDataUtil testDataUtil = new TestDataUtil();

    @Test
    public void save_ReturnsValidUser_WithValidUser(){
        User user = testDataUtil.validUser1();
        User result = userRepository.save(user);

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void getUserByUsername_ReturnsValidUser_WithValidUsername(){
        User user = testDataUtil.validUser1();
        userRepository.save(user);

        Optional<GetUserDto> result = userRepository.getUserByUsername(user.getUsername());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testUtilMappers.userToGetUserDtoMapper(user));

    }

    @Test
    public void getUserByUsername_ReturnsNoUser_WithInvalidUsername(){
        Optional<GetUserDto> result = userRepository.getUserByUsername("xyz");

        assertThat(result).isEmpty();
    }

    @Test
    public void getUserByVerificationCode_ReturnsValidUser_WithValidVerificationCode(){
        User user = testDataUtil.validUser1();
        userRepository.save(user);

        Optional<VerifyUserDto> result = userRepository.getUserByVerificationCode(user.getVerificationCode());

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void getUserByVerificationCode_ReturnsNoUser_WithInvalidVerificationCode(){
        Optional<VerifyUserDto> result = userRepository.getUserByVerificationCode("xyz");

        assertThat(result).isEmpty();
    }

    @Test
    public void findUserByUsername_ReturnsValidUser_WithValidUsername(){
        User user = testDataUtil.validUser1();
        userRepository.save(user);

        Optional<User> result = userRepository.findUserByUsername(user.getUsername());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void findUserByUsername_ReturnsNoUser_WithInvalidUsername(){
        Optional<User> result = userRepository.findUserByUsername("xyz");

        assertThat(result).isEmpty();
    }

    @Test
    public void enableUser_EnablesTheRespectiveUserAndDeletesTheVerificationCode_WithValidUser(){
        User user = testDataUtil.validUser1();
        user.setVerificationCodeValidTill(123L);
        userRepository.saveAndFlush(user);

        Optional<User> result = userRepository.findUserByUsername(user.getUsername());

        assertThat(result).isPresent();
        assertThat(result.get().getVerificationCode()).isNotNull();
        assertThat(result.get().isEnabled()).isEqualTo(false);
        assertThat(result.get().getVerificationCodeValidTill()).isEqualTo(user.getVerificationCodeValidTill());

        entityManager.clear();

        userRepository.enableUser(user.getUsername());

        Optional<User> result2 = userRepository.findUserByUsername(user.getUsername());

        assertThat(result2).isPresent();
        assertThat(result2.get().getVerificationCode()).isNull();
        assertThat(result2.get().isEnabled()).isEqualTo(true);
        assertThat(result2.get().getVerificationCodeValidTill()).isNull();

    }

    @Test
    public void getAdminByUsername_ReturnsValidAdmin_WithValidUsername(){
        User user = testDataUtil.validUser1();
        user.setRole("ROLE_ADMIN");

        userRepository.save(user);

        Optional<GetUserByAdminDto> result = userRepository.getAdminByUsername(user.getUsername());

        assertThat(result).isPresent();
        result.get().setCreatedAt(result.get().getCreatedAt().minusNanos(result.get().getCreatedAt().getNano()));
        assertThat(result.get()).isEqualTo(testUtilMappers.userToGetUserByAdminDto(user));
    }

    @Test
    public void getAllUsers_ReturnsValidPageAndAdmins_WithValidData(){
        User user = testDataUtil.validUser1();
        user.setRole("ROLE_ADMIN");
        User user2 = testDataUtil.validUser2();
        user2.setRole("ROLE_ADMIN");
        User user3 = testDataUtil.validUser3();

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        PageRequest pageRequest = PageRequest.of(0,5);
        Page<GetUserByAdminDto> result = userRepository.getAllUsers("ROLE_ADMIN",pageRequest);

        assertThat(result.stream().toList()).hasSize(2);
    }

    @Test
    public void getAllUsers_ReturnsValidPageAndUsers_WithValidData(){
        User user = testDataUtil.validUser1();
        user.setRole("ROLE_ADMIN");
        User user2 = testDataUtil.validUser2();
        user2.setRole("ROLE_ADMIN");
        User user3 = testDataUtil.validUser3();

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        PageRequest pageRequest = PageRequest.of(0,5);
        Page<GetUserByAdminDto> result = userRepository.getAllUsers("ROLE_USER",pageRequest);

        assertThat(result.stream().toList()).hasSize(1);
    }

    @Test
    public void getUsersBySearch_ReturnsValidPageAndAdmins_WithValidData(){
        User user = testDataUtil.validUser1();
        user.setRole("ROLE_ADMIN");
        User user2 = testDataUtil.validUser2();
        user2.setRole("ROLE_ADMIN");
        User user3 = testDataUtil.validUser3();

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        PageRequest pageRequest = PageRequest.of(0,5);
        Page<GetUserByAdminDto> result =
                userRepository.getUsersBySearch("i","ROLE_ADMIN",pageRequest);

        assertThat(result.stream().toList()).hasSize(2);

    }

    @Test
    public void getUsersBySearch_ReturnsValidPageAndUsers_WithValidData(){
        User user = testDataUtil.validUser1();
        user.setRole("ROLE_ADMIN");
        User user2 = testDataUtil.validUser2();
        user2.setRole("ROLE_ADMIN");
        User user3 = testDataUtil.validUser3();

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        PageRequest pageRequest = PageRequest.of(0,5);
        Page<GetUserByAdminDto> result =
                userRepository.getUsersBySearch("i","ROLE_USER",pageRequest);

        assertThat(result.stream().toList()).hasSize(1);

    }



}
