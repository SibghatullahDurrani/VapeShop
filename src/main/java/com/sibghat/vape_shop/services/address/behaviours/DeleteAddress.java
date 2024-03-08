package com.sibghat.vape_shop.services.address.behaviours;

import com.sibghat.vape_shop.repositories.AddressRepository;
import com.sibghat.vape_shop.repositories.UserRepository;
import com.sibghat.vape_shop.services.address.behaviours.interfaces.IDeleteAddressBehaviour;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeleteAddress implements IDeleteAddressBehaviour {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public DeleteAddress(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAddress(String username, Long addressId) {

        if(!addressRepository.existsById(addressId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (!userRepository.existsByAddressesIdAndUsername(addressId,username)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else{
            addressRepository.deleteById(addressId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
