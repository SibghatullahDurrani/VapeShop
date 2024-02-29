package com.sibghat.vape_shop.mappers.user;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.mappers.IMapper;
import org.springframework.stereotype.Component;

@Component
public class AddUserDtoToUserMapper implements IMapper<AddUserDto, User> {
    @Override
    public User mapFrom(AddUserDto addUserDto) {
        return User.builder()
                .username(addUserDto.getUsername())
                .firstName(addUserDto.getFirstName())
                .lastName(addUserDto.getLastName())
                .email(addUserDto.getEmail())
                .password(addUserDto.getPassword())
                .contactNumber(addUserDto.getContactNumber())
                .enabled(addUserDto.getEnabled())
                .build();
    }
}
