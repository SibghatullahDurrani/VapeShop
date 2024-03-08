package com.sibghat.vape_shop.services.address.behaviours;

import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.repositories.AddressRepository;
import com.sibghat.vape_shop.services.address.behaviours.interfaces.IGetAddressBehaviour;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAddress implements IGetAddressBehaviour {

    private final AddressRepository addressRepository;

    public GetAddress(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public ResponseEntity<List<GetAddressDto>> getAddress(String username) {
        return new ResponseEntity<>(
                addressRepository.getAddresses(username),
                HttpStatus.OK
        );
    }
}
