package com.sibghat.vape_shop.domains.ids;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductId implements Serializable {

    private BigInteger productId;
    private BigInteger orderId;
}
