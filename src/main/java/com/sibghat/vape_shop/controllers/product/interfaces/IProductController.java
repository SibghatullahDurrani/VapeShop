package com.sibghat.vape_shop.controllers.product.interfaces;

import com.sibghat.vape_shop.dtos.product.CreateProductDto;
import com.sibghat.vape_shop.dtos.product.GetProductDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IProductController {

    @PostMapping("/product")
    @PreAuthorize("""
    hasRole("ADMIN")
""")
    ResponseEntity<GetProductDto> createProduct(@Valid @RequestBody CreateProductDto productToCreate);
}
