package com.sibghat.vape_shop.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class GetUserByAdminDto {

    @NotBlank
    private String username;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String contactNumber;
    @NotNull
    private Boolean enabled;
    @NotNull
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    @NotBlank
    private String createdBy;
    private String lastModifiedBy;

}
