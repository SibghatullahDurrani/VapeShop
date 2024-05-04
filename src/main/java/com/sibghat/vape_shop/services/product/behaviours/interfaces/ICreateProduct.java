package com.sibghat.vape_shop.services.product.behaviours.interfaces;

import com.sibghat.vape_shop.dtos.product.CreateProductDto;
import com.sibghat.vape_shop.dtos.product.GetProductDto;
import org.springframework.http.ResponseEntity;

public interface ICreateProduct {
    ResponseEntity<GetProductDto> createProduct(CreateProductDto productToCreate);
}
