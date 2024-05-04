package com.sibghat.vape_shop.dtos.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class GetCategoryDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    @NotBlank
    private String createdBy;

    private String lastModifiedBy;
}
