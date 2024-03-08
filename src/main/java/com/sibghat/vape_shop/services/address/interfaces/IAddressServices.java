package com.sibghat.vape_shop.services.address.interfaces;

import com.sibghat.vape_shop.dtos.address.AddAddressDto;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.dtos.address.UpdateAddressDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAddressServices {

    ResponseEntity<GetAddressDto> addAddress(String username, AddAddressDto addressToAdd);

    ResponseEntity<List<GetAddressDto>> getAddress(String username);

    ResponseEntity<GetAddressDto> updateAddressDto(Long addressId, String username, UpdateAddressDto updateAddressDto);
    ResponseEntity<HttpStatus> deleteAddress(String username, Long addressId);
}
