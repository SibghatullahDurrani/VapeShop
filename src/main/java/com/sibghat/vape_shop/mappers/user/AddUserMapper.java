package com.sibghat.vape_shop.mappers.user;

import com.sibghat.vape_shop.dtos.user.AddUserDto;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.mappers.IMapper;
import org.springframework.stereotype.Component;

@Component
public class AddUserMapper implements IMapper<User, AddUserDto> {
    @Override
    public User mapTo(AddUserDto addUserDto) {
        return User.builder()
                .username(addUserDto.getUsername())
                .email(addUserDto.getEmail())
                .password(addUserDto.getPassword())
                .contactNumber(addUserDto.getContactNumber())
                .enabled(addUserDto.getEnabled())
                .build();
    }

    @Override
    public AddUserDto mapFrom(User user) {
        return AddUserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .contactNumber(user.getContactNumber())
                .enabled(user.isEnabled())
                .build();
    }
}
