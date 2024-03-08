package com.sibghat.vape_shop.services.address.behaviours;

import com.sibghat.vape_shop.domains.Address;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.dtos.address.UpdateAddressDto;
import com.sibghat.vape_shop.mappers.address.AddressToGetAddressDtoMapper;
import com.sibghat.vape_shop.repositories.AddressRepository;
import com.sibghat.vape_shop.services.address.behaviours.interfaces.IUpdateAddressBehaviour;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class UpdateAddress implements IUpdateAddressBehaviour {

    private final AddressRepository addressRepository;
    private final AddressToGetAddressDtoMapper addressToGetAddressDtoMapper;

    public UpdateAddress(AddressRepository addressRepository, AddressToGetAddressDtoMapper addressToGetAddressDtoMapper) {
        this.addressRepository = addressRepository;
        this.addressToGetAddressDtoMapper = addressToGetAddressDtoMapper;
    }

    @Override
    public ResponseEntity<GetAddressDto> updateAddress(Long addressId, String username, UpdateAddressDto addressToUpdate) {
        Optional<Address> address = addressRepository.findAddressById(addressId);
        if(address.isPresent()){
            if(!Objects.equals(address.get().getUser().getUsername(), username)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else{
                address.get().setLastModifiedAt(LocalDateTime.now());
                address.get().setLastModifiedBy(username);
                address.get().setStreet(addressToUpdate.getStreet());
                address.get().setCity(addressToUpdate.getCity());
                address.get().setState(addressToUpdate.getState());
                address.get().setCountry(addressToUpdate.getCountry());
                address.get().setZip(addressToUpdate.getZip());

                return new ResponseEntity<>(
                        addressToGetAddressDtoMapper.mapFrom(addressRepository.save(address.get())),
                        HttpStatus.OK
                );
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
