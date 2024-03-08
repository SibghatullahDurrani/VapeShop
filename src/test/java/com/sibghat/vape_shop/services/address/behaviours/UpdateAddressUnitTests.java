package com.sibghat.vape_shop.services.address.behaviours;

import com.sibghat.vape_shop.AddressTestDataUtil;
import com.sibghat.vape_shop.domains.Address;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.dtos.address.UpdateAddressDto;
import com.sibghat.vape_shop.mappers.address.AddressToGetAddressDtoMapper;
import com.sibghat.vape_shop.repositories.AddressRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateAddressUnitTests {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressToGetAddressDtoMapper addressToGetAddressDtoMapper;

    private final AddressTestDataUtil addressTestDataUtil = new AddressTestDataUtil();

    @InjectMocks
    private UpdateAddress underTest;

    @Test
    void updateAddress_ReturnsHTTP200OK_WithValidAddressIdAndUsername(){
        Long addressId = 1L;
        String username = "abc";
        UpdateAddressDto updateAddressDto = addressTestDataUtil.updateAddressDto();
        Optional<Address> address = Optional.ofNullable(addressTestDataUtil.address());
        GetAddressDto getAddressDto = addressTestDataUtil.getAddressDto();

        address.get().setUser(
                User.builder()
                        .username(username)
                        .build()
        );

        when(addressRepository.findAddressById(addressId))
                .thenReturn(address);

        when(addressRepository.save(address.get()))
                .thenReturn(address.get());

        when(addressToGetAddressDtoMapper.mapFrom(address.get()))
                .thenReturn(getAddressDto);

        ResponseEntity<GetAddressDto> result = underTest.updateAddress(addressId, username, updateAddressDto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void updateAddress_ReturnsHTTP403Forbidden_WithUpdatingAddressThatDoesntBelongToTheUser(){
        Long addressId = 1L;
        String username = "abc";
        UpdateAddressDto updateAddressDto = addressTestDataUtil.updateAddressDto();
        Optional<Address> address = Optional.ofNullable(addressTestDataUtil.address());

        address.get().setUser(
                User.builder()
                        .username("xyz")
                        .build()
        );

        when(addressRepository.findAddressById(addressId))
                .thenReturn(address);


        ResponseEntity<GetAddressDto> result = underTest.updateAddress(addressId, username, updateAddressDto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }

    @Test
    void updateAddress_ReturnsHTTP404NotFound_WithInvalidAddressId(){
        Long addressId = 1L;
        String username = "abc";
        UpdateAddressDto updateAddressDto = addressTestDataUtil.updateAddressDto();


        when(addressRepository.findAddressById(addressId))
                .thenReturn(Optional.empty());


        ResponseEntity<GetAddressDto> result = underTest.updateAddress(addressId, username, updateAddressDto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

}