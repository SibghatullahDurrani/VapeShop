package com.sibghat.vape_shop.repositories;

import com.sibghat.vape_shop.TestDataUtil;
import com.sibghat.vape_shop.domains.Address;
import com.sibghat.vape_shop.domains.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressRepositoryUnitTests {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;
    private final TestDataUtil testDataUtil = new TestDataUtil();

    @Test
    public void saveAddress_AddsAddress_WithValidAddress(){

        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);

        Address addressDto = Address.builder()
                .country("Pakistan")
                .state("Balochistan")
                .city("Quetta")
                .zip("12345")
                .street("Example Street")
                .createdAt(LocalDateTime.now())
                .createdBy(user.getUsername())
                .user(User.builder()
                        .id(1L)
                        .build())
                .build();

        addressRepository.saveAndFlush(addressDto);

        entityManager.clear();

        Optional<User> result = userRepository.findUserByUsername(user.getUsername());

        assertThat(result).isPresent();
        assertThat(result.get().getAddresses()).hasSize(1);


    }
    @Test
    public void countAddressByUserId_ReturnsValidCount_WithValidUserId(){

        User user = testDataUtil.validUser1();
        userRepository.saveAndFlush(user);

        Address addressDto = Address.builder()
                .country("Pakistan")
                .state("Balochistan")
                .city("Quetta")
                .zip("12345")
                .street("Example Street")
                .createdAt(LocalDateTime.now())
                .createdBy(user.getUsername())
                .user(User.builder()
                        .id(1L)
                        .build())
                .build();

        Address addressDto2 = Address.builder()
                .country("Pakistan")
                .state("Balochistan")
                .city("Quetta")
                .zip("12345")
                .street("Example Street")
                .createdAt(LocalDateTime.now())
                .createdBy(user.getUsername())
                .user(User.builder()
                        .id(1L)
                        .build())
                .build();

        Address addressDto3 = Address.builder()
                .country("Pakistan")
                .state("Balochistan")
                .city("Quetta")
                .zip("12345")
                .street("Example Street")
                .createdAt(LocalDateTime.now())
                .createdBy(user.getUsername())
                .user(User.builder()
                        .id(1L)
                        .build())
                .build();


        addressRepository.saveAndFlush(addressDto);
        addressRepository.saveAndFlush(addressDto2);
        addressRepository.saveAndFlush(addressDto3);

        entityManager.clear();

        int result = addressRepository.countAddressByUserId(user.getId());

        assertThat(result).isEqualTo(3);


    }

}