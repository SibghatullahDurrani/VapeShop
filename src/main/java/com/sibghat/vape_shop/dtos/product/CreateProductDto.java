package com.sibghat.vape_shop.dtos.product;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class CreateProductDto {

    @NotBlank
    private String vendorName;

    @NotBlank
    private String productName;

    @NotNull
    @Positive
    @DecimalMax("99999.00")
    @Column(
            scale = 2,
            precision = 7
    )
    private BigDecimal purchaseUnitPrice;

    @NotNull
    @Positive
    @DecimalMax("99999.00")
    @Column(
            scale = 2,
            precision = 7
    )
    private BigDecimal saleUnitPrice;

    @PositiveOrZero
    @NotNull
    @DecimalMax("1.00")
    @Column(
            scale = 2,
            precision = 3
    )
    private BigDecimal discount;

    @PositiveOrZero
    private int stock;
}
