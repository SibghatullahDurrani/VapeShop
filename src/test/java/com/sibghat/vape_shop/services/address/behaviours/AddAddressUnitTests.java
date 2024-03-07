package com.sibghat.vape_shop.services.address.behaviours;

import com.sibghat.vape_shop.mappers.address.AddAddressDtoToAddressMapper;
import com.sibghat.vape_shop.mappers.address.AddressToGetAddressDtoMapper;
import com.sibghat.vape_shop.repositories.AddressRepository;
import com.sibghat.vape_shop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddAddressUnitTests {

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AddAddressDtoToAddressMapper addAddressDtoToAddressMapper;
    @Mock
    private AddressToGetAddressDtoMapper addressToGetAddressDtoMapper;

    @InjectMocks
    private AddAddress underTest;

    // TODO: add unit tests and integrate add address to client and admin controllers

}