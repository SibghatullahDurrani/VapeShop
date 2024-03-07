package com.sibghat.vape_shop.mappers.address;

import com.sibghat.vape_shop.domains.Address;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.address.AddAddressDto;
import com.sibghat.vape_shop.mappers.IMapper;
import org.springframework.stereotype.Component;

@Component
public class AddAddressDtoToAddressMapper implements IMapper<AddAddressDto, Address> {

    @Override
    public Address mapFrom(AddAddressDto addAddressDto) {
        return Address.builder()
                .street(addAddressDto.getStreet())
                .city(addAddressDto.getCity())
                .state(addAddressDto.getState())
                .country(addAddressDto.getCountry())
                .zip(addAddressDto.getZip())
                .user(User.builder()
                        .id(addAddressDto.getUserId())
                        .build())
                .build();
    }
}
