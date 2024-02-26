package com.sibghat.vape_shop.domains.ids;

import com.sibghat.vape_shop.domains.Cart;
import com.sibghat.vape_shop.domains.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProductId implements Serializable {


    private Cart cartId;
    private Product productId;
}
