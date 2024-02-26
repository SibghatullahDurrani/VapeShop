package com.sibghat.vape_shop.domains.ids;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductId implements Serializable {

    private Long productId;
    private Long orderId;
}
