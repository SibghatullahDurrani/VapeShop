package com.sibghat.vape_shop.services.category.interfaces;

import com.sibghat.vape_shop.dtos.category.AddCategoryDto;
import com.sibghat.vape_shop.dtos.category.GetCategoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryServices {
    ResponseEntity<GetCategoryDto> addCategory(AddCategoryDto categoryToAdd, String createdBy);
    ResponseEntity<List<GetCategoryDto>> getCategories();
}
