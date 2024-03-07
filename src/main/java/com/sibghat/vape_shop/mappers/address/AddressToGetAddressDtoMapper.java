package com.sibghat.vape_shop.mappers.address;

import com.sibghat.vape_shop.domains.Address;
import com.sibghat.vape_shop.dtos.address.GetAddressDto;
import com.sibghat.vape_shop.mappers.IMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressToGetAddressDtoMapper implements IMapper<Address, GetAddressDto> {

    @Override
    public GetAddressDto mapFrom(Address address) {
        return GetAddressDto.builder()
                .id(address.getId())
                .state(address.getState())
                .street(address.getStreet())
                .city(address.getCity())
                .country(address.getCountry())
                .zip(address.getZip())
                .build();
    }
}
