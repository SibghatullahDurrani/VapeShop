package com.sibghat.vape_shop.services.category;

import com.sibghat.vape_shop.dtos.category.AddCategoryDto;
import com.sibghat.vape_shop.dtos.category.GetCategoryDto;
import com.sibghat.vape_shop.services.category.behaviours.interfaces.IAddCategory;
import com.sibghat.vape_shop.services.category.behaviours.interfaces.IGetCategories;
import com.sibghat.vape_shop.services.category.interfaces.ICategoryServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServices implements ICategoryServices {

    private final IAddCategory addCategoryBehaviour;
    private final IGetCategories getCategoriesBehaviour;

    public CategoryServices(IAddCategory addCategory) {
        this.addCategoryBehaviour = addCategory;
    }

    @Override
    public ResponseEntity<GetCategoryDto> addCategory(AddCategoryDto categoryToAdd, String createdBy) {
        return addCategoryBehaviour.addCategory(categoryToAdd, createdBy);
    }

    @Override
    public ResponseEntity<List<GetCategoryDto>> getCategories() {
        return null;
    }
}
