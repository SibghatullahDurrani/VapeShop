package com.sibghat.vape_shop.services.address.behaviours;

import com.sibghat.vape_shop.domains.Address;
import com.sibghat.vape_shop.dtos.address.AddAddressDto;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.mappers.address.AddAddressDtoToAddressMapper;
import com.sibghat.vape_shop.mappers.address.AddressToGetAddressDtoMapper;
import com.sibghat.vape_shop.repositories.AddressRepository;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.address.behaviours.interfaces.IAddAddressBehaviour;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AddAddress implements IAddAddressBehaviour {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddAddressDtoToAddressMapper addAddressDtoToAddressMapper;
    private final AddressToGetAddressDtoMapper addressToGetAddressDtoMapper;

    public AddAddress(
            AddressRepository addressRepository, UserRepository userRepository,
            AddAddressDtoToAddressMapper addAddressDtoToAddressMapper, AddressToGetAddressDtoMapper addressToGetAddressDtoMapper
    ) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.addAddressDtoToAddressMapper = addAddressDtoToAddressMapper;
        this.addressToGetAddressDtoMapper = addressToGetAddressDtoMapper;
    }

    @Override
    public ResponseEntity<GetAddressDto> addAddress(String username, AddAddressDto addressToAdd) {
        Optional<Long> userId = userRepository.getUserIdByUsername(username);
        if(userId.isPresent()){
            if(addressRepository.countAddressByUserId(userId.get()) >= 2){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            addressToAdd.setUserId(userId.get());
            Address address = addAddressDtoToAddressMapper.mapFrom(addressToAdd);
            address.setCreatedAt(LocalDateTime.now());
            address.setCreatedBy(username);
            return new ResponseEntity<>(
                addressToGetAddressDtoMapper.mapFrom(addressRepository.save(address)),
                    HttpStatus.CREATED
            );
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
