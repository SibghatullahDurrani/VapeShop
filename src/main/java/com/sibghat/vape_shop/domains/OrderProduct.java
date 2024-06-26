package com.sibghat.vape_shop.domains;

import com.sibghat.vape_shop.domains.ids.OrderProductId;
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
@Table(name = "orders_products")
@IdClass(OrderProductId.class)
public class OrderProduct {

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(
            name = "product_id",
            nullable = false,
            columnDefinition = "BIGINT"
    )
    private Product productId;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "BIGINT"
    )
    private Order orderId;

    @NotNull
    @Positive
    private int quantity;

}
