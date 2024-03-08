package com.sibghat.vape_shop.services.address;

import com.sibghat.vape_shop.dtos.address.AddAddressDto;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.dtos.address.UpdateAddressDto;
import com.sibghat.vape_shop.services.address.behaviours.interfaces.IAddAddressBehaviour;
import com.sibghat.vape_shop.services.address.behaviours.interfaces.IDeleteAddressBehaviour;
import com.sibghat.vape_shop.services.address.behaviours.interfaces.IGetAddressBehaviour;
import com.sibghat.vape_shop.services.address.behaviours.interfaces.IUpdateAddressBehaviour;
import com.sibghat.vape_shop.services.address.interfaces.IAddressServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServices implements IAddressServices {

    private final IAddAddressBehaviour addAddressBehaviour;
    private final IGetAddressBehaviour getAddressBehaviour;
    private final IUpdateAddressBehaviour updateAddressBehaviour;
    private final IDeleteAddressBehaviour deleteAddressBehaviour;

    public AddressServices(
            IAddAddressBehaviour addAddressBehaviour,
            IGetAddressBehaviour getAddressBehaviour,
            IUpdateAddressBehaviour updateAddressBehaviour, IDeleteAddressBehaviour deleteAddressBehaviour
    ) {
        this.addAddressBehaviour = addAddressBehaviour;
        this.getAddressBehaviour = getAddressBehaviour;
        this.updateAddressBehaviour = updateAddressBehaviour;
        this.deleteAddressBehaviour = deleteAddressBehaviour;
    }

    @Override
    public ResponseEntity<GetAddressDto> addAddress(String username, AddAddressDto addressToAdd){
        return addAddressBehaviour.addAddress(username,addressToAdd);
    }

    @Override
    public ResponseEntity<List<GetAddressDto>> getAddress(String username) {
        return getAddressBehaviour.getAddress(username);
    }

    @Override
    public ResponseEntity<GetAddressDto> updateAddressDto(Long addressId, String username, UpdateAddressDto updateAddressDto) {
        return updateAddressBehaviour.updateAddress(addressId,username,updateAddressDto);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAddress(String username, Long addressId) {
        return deleteAddressBehaviour.deleteAddress(username,addressId);
    }


}
