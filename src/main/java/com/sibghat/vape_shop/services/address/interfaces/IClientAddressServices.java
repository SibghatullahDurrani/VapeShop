package com.sibghat.vape_shop.services.address.interfaces;

import com.sibghat.vape_shop.dtos.address.AddAddressDto;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import org.springframework.http.ResponseEntity;

public interface IClientAddressServices {

    ResponseEntity<GetAddressDto> addAddress(String username, AddAddressDto addressToAdd);
}
