package com.sibghat.vape_shop.services.address.behaviours.interfaces;

import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IGetAddressBehaviour {

    ResponseEntity<List<GetAddressDto>> getAddress(String username);

}
