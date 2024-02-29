package com.sibghat.vape_shop.dtos.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class VerifyUserDto {

    @NotNull
    private String username;

    @NotNull
    private Long verificationCodeValidTill;
}
