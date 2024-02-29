package com.sibghat.vape_shop.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class GetAdminDto {

    private String username;
    private String email;
    private String contactNumber;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;

}
