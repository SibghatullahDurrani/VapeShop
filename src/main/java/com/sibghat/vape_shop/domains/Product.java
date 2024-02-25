package com.sibghat.vape_shop.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_generator")
    @SequenceGenerator(name = "products_generator", sequenceName = "products_seq", allocationSize = 1)
    private Integer id;

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

    @NotNull
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime lastModifiedAt;

    @NotNull
    @OneToOne
    @JoinColumn(
            name = "created_by"
    )
    private User createdBy;

    @NotNull
    @OneToOne
    @JoinColumn(
            name = "last_modified_by"
    )
    private User lastModifiedBy;


}
