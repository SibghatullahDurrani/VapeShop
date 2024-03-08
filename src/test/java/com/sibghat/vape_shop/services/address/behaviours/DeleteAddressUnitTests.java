package com.sibghat.vape_shop.services.address.behaviours;

import com.sibghat.vape_shop.repositories.AddressRepository;
import com.sibghat.vape_shop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteAddressUnitTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private DeleteAddress underTest;

    @Test
    void deleteAddress_ReturnsHTTP204NoContent_WithValidUsernameAndAddressId(){
        String username = "xyz";
        Long addressId = 1L;

        when(addressRepository.existsById(addressId))
                .thenReturn(true);

        when(userRepository.existsByAddressesIdAndUsername(addressId,username))
                .thenReturn(true);

        ResponseEntity<HttpStatus> result = underTest.deleteAddress(username, addressId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deleteAddress_ReturnsHTTP403Forbidden_WithInvalidAddressId(){
        String username = "xyz";
        Long addressId = 1L;

        when(addressRepository.existsById(addressId))
                .thenReturn(false);


        ResponseEntity<HttpStatus> result = underTest.deleteAddress(username, addressId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteAddress_ReturnsHTTP403Forbidden_WithInValidUsernameAndAddressId(){
        String username = "xyz";
        Long addressId = 1L;

        when(addressRepository.existsById(addressId))
                .thenReturn(true);

        when(userRepository.existsByAddressesIdAndUsername(addressId,username))
                .thenReturn(false);

        ResponseEntity<HttpStatus> result = underTest.deleteAddress(username, addressId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}