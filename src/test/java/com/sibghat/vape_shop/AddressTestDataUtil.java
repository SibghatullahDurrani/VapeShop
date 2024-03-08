package com.sibghat.vape_shop;

import com.sibghat.vape_shop.domains.Address;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.address.AddAddressDto;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.dtos.address.UpdateAddressDto;

import java.time.LocalDateTime;

public class AddressTestDataUtil {

    public AddAddressDto addAddressDto(){
        return AddAddressDto.builder()
                .street("xyz")
                .city("xyz")
                .state("xyz")
                .country("xyz")
                .userId(1L)
                .zip("12345")
                .build();
    }

    public Address address(){
        return Address.builder()
                .street("xyz")
                .city("xyz")
                .state("xyz")
                .country("xyz")
                .zip("12345")
                .createdBy("xyz")
                .createdAt(LocalDateTime.now())
                .user(
                        User.builder()
                                .id(1L)
                                .build()
                )
                .build();
    }

    public GetAddressDto getAddressDto(){
        return GetAddressDto.builder()
                .id(1L)
                .street("xyz")
                .city("xyz")
                .state("xyz")
                .country("xyz")
                .zip("12345")
                .build();
    }

    public UpdateAddressDto updateAddressDto(){
        return UpdateAddressDto.builder()
                .street("xyz")
                .city("xyz")
                .state("xyz")
                .country("xyz")
                .zip("12345")
                .build();
    }
}
