package com.sibghat.vape_shop.controllers.address;

import com.sibghat.vape_shop.controllers.address.interfaces.IAddressController;
import com.sibghat.vape_shop.dtos.address.AddAddressDto;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.dtos.address.UpdateAddressDto;
import com.sibghat.vape_shop.services.address.AddressServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddressController implements IAddressController {


    private final AddressServices addressServices;

    public AddressController(AddressServices addressServices) {
        this.addressServices = addressServices;
    }

    @Override
    public ResponseEntity<GetAddressDto> addAddress(
            @PathVariable String username,
            @Valid @RequestBody AddAddressDto addressToAdd
    ) {
        return addressServices.addAddress(username,addressToAdd);
    }

    @Override
    public ResponseEntity<List<GetAddressDto>> getAddress(@PathVariable String username){
        return addressServices.getAddress(username);
    }

    @Override
    public ResponseEntity<GetAddressDto> updateAddress(
            @PathVariable String username,
            @PathVariable(name = "address_id") Long addressId,
            @Valid @RequestBody UpdateAddressDto updateAddressDto
    ){
        return addressServices.updateAddressDto(addressId,username,updateAddressDto);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAddress(
            @PathVariable String username,
            @PathVariable(name = "address_id") Long addressId
    ){
        return addressServices.deleteAddress(username,addressId);
    }
}
