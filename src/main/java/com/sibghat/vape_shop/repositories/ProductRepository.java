package com.sibghat.vape_shop.repositories;

import com.sibghat.vape_shop.domains.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
