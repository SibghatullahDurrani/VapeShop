package com.sibghat.vape_shop.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UpdatePasswordDto {

    @NotBlank
    private String previousPassword;
    @NotBlank
    private String newPassword;

}
