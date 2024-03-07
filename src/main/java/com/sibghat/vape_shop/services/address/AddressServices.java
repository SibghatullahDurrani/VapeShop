package com.sibghat.vape_shop.services.address;

import com.sibghat.vape_shop.dtos.address.AddAddressDto;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.services.address.behaviours.interfaces.IAddAddressBehaviour;
import org.springframework.http.ResponseEntity;

public abstract class AddressServices {

    private final IAddAddressBehaviour addAddressBehaviour;

    protected AddressServices(IAddAddressBehaviour addAddressBehaviour) {
        this.addAddressBehaviour = addAddressBehaviour;
    }

    public ResponseEntity<GetAddressDto> addAddress(String username, AddAddressDto addressToAdd){
        return addAddressBehaviour.addAddress(username,addressToAdd);
    }
}
