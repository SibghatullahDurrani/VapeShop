package com.sibghat.vape_shop.services.address.behaviours.interfaces;

import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.dtos.address.UpdateAddressDto;
import org.springframework.http.ResponseEntity;

public interface IUpdateAddressBehaviour {

    ResponseEntity<GetAddressDto> updateAddress(Long addressId, String username, UpdateAddressDto addressToUpdate);
}
