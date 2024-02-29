package com.sibghat.vape_shop.mappers.user;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.mappers.IMapper;
import org.springframework.stereotype.Component;

@Component
public class UserToAddUserDtoMapper implements IMapper<User, AddUserDto> {
    @Override
    public AddUserDto mapFrom(User user) {
        return AddUserDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .contactNumber(user.getContactNumber())
                .enabled(user.isEnabled())
                .build();
    }
}
