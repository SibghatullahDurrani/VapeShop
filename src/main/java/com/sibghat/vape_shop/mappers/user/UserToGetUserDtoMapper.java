package com.sibghat.vape_shop.mappers.user;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.mappers.IMapper;
import org.springframework.stereotype.Component;

@Component
public class UserToGetUserDtoMapper implements IMapper<User, GetUserDto> {
    @Override
    public GetUserDto mapFrom(User user) {
        return GetUserDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .contactNumber(user.getContactNumber())
                .build();
    }
}
