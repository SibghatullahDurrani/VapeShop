package com.sibghat.vape_shop.services.category.behaviours;

import com.sibghat.vape_shop.domains.Category;
import com.sibghat.vape_shop.dtos.category.AddCategoryDto;
import com.sibghat.vape_shop.dtos.category.GetCategoryDto;
import com.sibghat.vape_shop.mappers.category.AddCategoryDtoToCategoryMapper;
import com.sibghat.vape_shop.mappers.category.CategoryToGetCategoryDtoMapper;
import com.sibghat.vape_shop.repositories.CategoryRepository;
import com.sibghat.vape_shop.services.category.behaviours.interfaces.IAddCategory;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddCategory implements IAddCategory {

    private final CategoryRepository categoryRepository;
    private final AddCategoryDtoToCategoryMapper addCategoryDtoToCategoryMapper;
    private final CategoryToGetCategoryDtoMapper categoryToGetCategoryDtoMapper;

    public AddCategory(CategoryRepository categoryRepository, AddCategoryDtoToCategoryMapper addCategoryDtoToCategoryMapper, CategoryToGetCategoryDtoMapper categoryToGetCategoryDtoMapper) {
        this.categoryRepository = categoryRepository;
        this.addCategoryDtoToCategoryMapper = addCategoryDtoToCategoryMapper;
        this.categoryToGetCategoryDtoMapper = categoryToGetCategoryDtoMapper;
    }

    @Override
    public ResponseEntity<GetCategoryDto> addCategory(AddCategoryDto addCategoryDto, String createdBy) {
        if(categoryRepository.existsByName(addCategoryDto.getName())){
            throw new EntityExistsException("category: ");
        }else{
            Category category = addCategoryDtoToCategoryMapper.mapFrom(addCategoryDto);
            category.setCreatedBy(createdBy);
            Category savedCategory = categoryRepository.save(category);
            return new ResponseEntity<>(
                    categoryToGetCategoryDtoMapper.mapFrom(savedCategory), HttpStatus.CREATED);
        }
    }
}
