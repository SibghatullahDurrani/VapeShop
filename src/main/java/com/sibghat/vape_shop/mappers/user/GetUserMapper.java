package com.sibghat.vape_shop.mappers.user;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.GetUserDto;
import com.sibghat.vape_shop.mappers.IMapper;
import org.springframework.stereotype.Component;

@Component
public class GetUserMapper implements IMapper<User, GetUserDto> {
    @Override
    public User mapTo(GetUserDto getUserDto) {
        return User.builder()
                .username(getUserDto.getUsername())
                .email(getUserDto.getEmail())
                .contactNumber(getUserDto.getContactNumber())
                .build();
    }

    @Override
    public GetUserDto mapFrom(User user) {
        return GetUserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .contactNumber(user.getContactNumber())
                .build();
    }
}
