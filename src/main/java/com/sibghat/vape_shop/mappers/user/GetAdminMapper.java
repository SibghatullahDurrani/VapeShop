package com.sibghat.vape_shop.mappers.user;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.GetUserByAdminDto;
import com.sibghat.vape_shop.mappers.IMapper;
import org.springframework.stereotype.Component;

@Component
public class GetAdminMapper implements IMapper<User, GetUserByAdminDto> {

    @Override
    public User mapTo(GetUserByAdminDto getUserByAdminDto) {
        return User.builder()
                .username(getUserByAdminDto.getUsername())
                .email(getUserByAdminDto.getEmail())
                .contactNumber(getUserByAdminDto.getContactNumber())
                .enabled(getUserByAdminDto.getEnabled())
                .createdAt(getUserByAdminDto.getCreatedAt())
                .lastModifiedAt(getUserByAdminDto.getLastModifiedAt())
                .createdBy(getUserByAdminDto.getCreatedBy())
                .lastModifiedBy(getUserByAdminDto.getLastModifiedBy())
                .build();
    }

    @Override
    public GetUserByAdminDto mapFrom(User user) {
        return GetUserByAdminDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .contactNumber(user.getContactNumber())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .lastModifiedAt(user.getLastModifiedAt())
                .createdBy(user.getCreatedBy())
                .lastModifiedBy(user.getLastModifiedBy())
                .build();
    }
}
