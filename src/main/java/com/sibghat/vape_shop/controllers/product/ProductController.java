package com.sibghat.vape_shop.controllers.product;

import com.sibghat.vape_shop.controllers.product.interfaces.IProductController;
import com.sibghat.vape_shop.dtos.product.CreateProductDto;
import com.sibghat.vape_shop.dtos.product.GetProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController implements IProductController {



    @Override
    public ResponseEntity<GetProductDto> createProduct(CreateProductDto productToCreate) {
        return null;
    }
}
