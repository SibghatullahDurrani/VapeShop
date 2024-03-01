package com.sibghat.vape_shop.repositories;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import static org.assertj.core.api.Assertions.*;

import com.sibghat.vape_shop.dtos.user.VerifyUserDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryUnitTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;


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
        assertThat(result.get().getUsername()).isEqualTo(user.getUsername());
        assertThat(result.get().getFirstName()).isEqualTo(user.getFirstName());
        assertThat(result.get().getLastName()).isEqualTo(user.getLastName());
        assertThat(result.get().getEmail()).isEqualTo(user.getEmail());
        assertThat(result.get().getContactNumber()).isEqualTo(user.getContactNumber());
        assertThat(result.get().getVerificationCode()).isEqualTo(user.getVerificationCode());

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
        userRepository.saveAndFlush(user);

        Optional<User> result = userRepository.findUserByUsername(user.getUsername());

        assertThat(result).isPresent();
        assertThat(result.get().getVerificationCode()).isNotNull();
        assertThat(result.get().isEnabled()).isEqualTo(false);

        entityManager.clear();

        userRepository.enableUser(user.getUsername());

        Optional<User> result2 = userRepository.findUserByUsername(user.getUsername());

        assertThat(result2).isPresent();
        assertThat(result2.get().getVerificationCode()).isNull();
        assertThat(result2.get().isEnabled()).isEqualTo(true);

    }



}
