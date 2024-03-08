package com.sibghat.vape_shop.services.address.behaviours;

import com.sibghat.vape_shop.AddressTestDataUtil;
import com.sibghat.vape_shop.domains.Address;
import com.sibghat.vape_shop.dtos.address.AddAddressDto;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.mappers.address.AddAddressDtoToAddressMapper;
import com.sibghat.vape_shop.mappers.address.AddressToGetAddressDtoMapper;
import com.sibghat.vape_shop.repositories.AddressRepository;
import com.sibghat.vape_shop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddAddressUnitTests {

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AddAddressDtoToAddressMapper addAddressDtoToAddressMapper;
    @Mock
    private AddressToGetAddressDtoMapper addressToGetAddressDtoMapper;

    private final AddressTestDataUtil addressTestDataUtil = new AddressTestDataUtil();

    @InjectMocks
    private AddAddress underTest;

    @Test
    public void addAddress_Returns_Http201Created_WithValidData(){

        String username = "xyz";
        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        Optional<Long> userId = Optional.of(1L);
        Address address = addressTestDataUtil.address();
        GetAddressDto getAddressDto = addressTestDataUtil.getAddressDto();


        when(userRepository.getUserIdByUsername(username))
                .thenReturn(userId);

        when(addressRepository.countAddressByUserId(userId.get()))
                .thenReturn(1);

        when(addAddressDtoToAddressMapper.mapFrom(addAddressDto))
                .thenReturn(address);

        when(addressRepository.save(address))
                .thenReturn(address);

        when(addressToGetAddressDtoMapper.mapFrom(address))
                .thenReturn(getAddressDto);

        ResponseEntity<GetAddressDto> result = underTest.addAddress(username,addAddressDto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void addAddress_ReturnsHttp400BadRequest_WhenUserAlreadyHaveTwoAddresses(){
        String username = "xyz";
        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();
        Optional<Long> userId = Optional.of(1L);

        when(userRepository.getUserIdByUsername(username))
                .thenReturn(userId);

        when(addressRepository.countAddressByUserId(userId.get()))
                .thenReturn(2);

        ResponseEntity<GetAddressDto> result = underTest.addAddress(username,addAddressDto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void addAddress_ReturnsHttp404NotFound_WhenUserIdIsNotFound() {
        String username = "xyz";
        AddAddressDto addAddressDto = addressTestDataUtil.addAddressDto();

        when(userRepository.getUserIdByUsername(username))
                .thenReturn(Optional.empty());

        ResponseEntity<GetAddressDto> result = underTest.addAddress(username,addAddressDto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    // TODO: add unit tests and integrate add address to client and admin controllers

}