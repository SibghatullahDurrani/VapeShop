package com.sibghat.vape_shop.controllers.category.interfaces;

import com.sibghat.vape_shop.dtos.category.AddCategoryDto;
import com.sibghat.vape_shop.dtos.category.GetCategoryDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ICategoryController {

    @PostMapping("/category")
    @PreAuthorize("""
    hasRole("ADMIN")
""")
    ResponseEntity<GetCategoryDto> addCategory (@Valid @RequestBody AddCategoryDto categoryToAdd, Authentication a);

    @GetMapping("/category")
    @PreAuthorize("""
    hasRole("ADMIN")
""")
    ResponseEntity<List<GetCategoryDto>> getCategories();


}
