package com.sibghat.vape_shop.mappers.user;

import com.sibghat.vape_shop.domains.User;
import com.sibghat.vape_shop.dtos.user.GetAdminDto;
import com.sibghat.vape_shop.mappers.IMapper;
import org.springframework.stereotype.Component;

@Component
public class GetAdminMapper implements IMapper<User, GetAdminDto> {

    @Override
    public User mapTo(GetAdminDto getAdminDto) {
        return User.builder()
                .username(getAdminDto.getUsername())
                .email(getAdminDto.getEmail())
                .contactNumber(getAdminDto.getContactNumber())
                .enabled(getAdminDto.getEnabled())
                .createdAt(getAdminDto.getCreatedAt())
                .lastModifiedAt(getAdminDto.getLastModifiedAt())
                .createdBy(getAdminDto.getCreatedBy())
                .lastModifiedBy(getAdminDto.getLastModifiedBy())
                .build();
    }

    @Override
    public GetAdminDto mapFrom(User user) {
        return GetAdminDto.builder()
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
