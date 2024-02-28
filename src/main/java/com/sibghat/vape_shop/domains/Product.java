package com.sibghat.vape_shop.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    //https://stackoverflow.com/questions/23837561/jpa-2-0-many-to-many-with-extra-column

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_generator")
    @SequenceGenerator(name = "products_generator", sequenceName = "products_seq", allocationSize = 1)
    @Column(
            columnDefinition = "BIGINT"
    )
    private Long id;

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
    @FutureOrPresent
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @FutureOrPresent
    private LocalDateTime lastModifiedAt;

    private String createdBy;

    private String lastModifiedBy;

    @OneToMany(
            mappedBy = "product"
    )
    private List<Picture> pictures;

    @OneToMany(
            mappedBy = "productId"
    )
    private List<OrderProduct> orderProducts;

    @OneToMany(
            mappedBy = "productId"
    )
    private List<CartProduct> cartProducts;

    @ManyToMany
    @JoinTable(
            name = "products_categories",
            joinColumns = {
                    @JoinColumn(name = "product_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "category_id")
            }

    )
    private List<Category> categories;

    @OneToMany(
            mappedBy = "product"
    )
    private List<ProductsContent> productsContents;



}
