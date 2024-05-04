package com.sibghat.vape_shop.services.product.behaviours;

import com.sibghat.vape_shop.dtos.product.CreateProductDto;
import com.sibghat.vape_shop.dtos.product.GetProductDto;
import com.sibghat.vape_shop.services.product.behaviours.interfaces.ICreateProduct;
import org.springframework.http.ResponseEntity;

public class CreateProduct implements ICreateProduct {
    @Override
    public ResponseEntity<GetProductDto> createProduct(CreateProductDto productToCreate) {
        return null;
    }
}
