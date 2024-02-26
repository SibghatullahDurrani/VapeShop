package com.sibghat.vape_shop.mappers.user;

import com.sibghat.vape_shop.dtos.user.AddClientDto;
import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.mappers.IMapper;
import org.springframework.stereotype.Component;

@Component
public class AddClientMapper implements IMapper<User, AddClientDto> {
    @Override
    public User mapTo(AddClientDto addClientDto) {
        return User.builder()
                .username(addClientDto.getUsername())
                .email(addClientDto.getEmail())
                .password(addClientDto.getPassword())
                .contactNumber(addClientDto.getContactNumber())
                .enabled(addClientDto.getEnabled())
                .build();
    }

    @Override
    public AddClientDto mapFrom(User user) {
        return AddClientDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .contactNumber(user.getContactNumber())
                .enabled(user.isEnabled())
                .build();
    }
}
