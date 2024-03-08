package com.sibghat.vape_shop.controllers.address;

import com.sibghat.vape_shop.controllers.address.interfaces.IAddressController;
import com.sibghat.vape_shop.dtos.address.AddAddressDto;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.services.address.AddressServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
