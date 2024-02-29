package com.sibghat.vape_shop.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GetUserDto {

    @NotNull
    private String username;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String contactNumber;
}
