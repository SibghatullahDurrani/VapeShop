package com.sibghat.vape_shop.repositories;

import com.sibghat.vape_shop.domains.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
