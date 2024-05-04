package com.sibghat.vape_shop.controllers.category;

import com.sibghat.vape_shop.controllers.category.interfaces.ICategoryController;
import com.sibghat.vape_shop.dtos.category.AddCategoryDto;
import com.sibghat.vape_shop.dtos.category.GetCategoryDto;
import com.sibghat.vape_shop.services.category.interfaces.ICategoryServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController implements ICategoryController {

    private final ICategoryServices categoryServices;

    public CategoryController(ICategoryServices categoryServices) {
        this.categoryServices = categoryServices;
    }
    @Override
    public ResponseEntity<GetCategoryDto> addCategory(@Valid @RequestBody AddCategoryDto categoryToAdd, Authentication a) {
        return categoryServices.addCategory(categoryToAdd, a.getName());
    }

    @Override
    public ResponseEntity<List<GetCategoryDto>> getCategories() {
        return categoryServices.getCategories();
    }
}
