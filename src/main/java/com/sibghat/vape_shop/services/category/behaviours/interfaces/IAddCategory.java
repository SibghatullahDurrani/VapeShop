package com.sibghat.vape_shop.services.category.behaviours.interfaces;

import com.sibghat.vape_shop.dtos.category.AddCategoryDto;
import com.sibghat.vape_shop.dtos.category.GetCategoryDto;
import org.springframework.http.ResponseEntity;

public interface IAddCategory {
    ResponseEntity<GetCategoryDto> addCategory(AddCategoryDto addCategoryDto, String createdBy);
}
