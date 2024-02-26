package com.sibghat.vape_shop.domains;

import com.sibghat.vape_shop.domains.ids.CartProductId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "carts_products")
@IdClass(CartProductId.class)
public class CartProduct {

    @Id
    @ManyToOne
    @JoinColumn(
            name = "cart_id",
            nullable = false,
            columnDefinition = "BIGINT"
    )
    private Cart cartId;

    @Id
    @ManyToOne
    @JoinColumn(
            name = "product_id",
            nullable = false,
            columnDefinition = "BIGINT"
    )
    private Product productId;

    @Positive
    @NotNull
    private int quantity;

}
