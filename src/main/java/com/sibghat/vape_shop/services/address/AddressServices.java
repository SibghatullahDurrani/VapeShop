package com.sibghat.vape_shop.services.address;

import com.sibghat.vape_shop.dtos.address.AddAddressDto;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.services.address.behaviours.interfaces.IAddAddressBehaviour;
import com.sibghat.vape_shop.services.address.interfaces.IAddressServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressServices implements IAddressServices {

    private final IAddAddressBehaviour addAddressBehaviour;

    public AddressServices(IAddAddressBehaviour addAddressBehaviour) {
        this.addAddressBehaviour = addAddressBehaviour;
    }

    @Override
    public ResponseEntity<GetAddressDto> addAddress(String username, AddAddressDto addressToAdd){
        return addAddressBehaviour.addAddress(username,addressToAdd);
    }
}
