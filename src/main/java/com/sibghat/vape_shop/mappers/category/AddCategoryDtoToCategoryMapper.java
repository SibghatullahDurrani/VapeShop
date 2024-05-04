package com.sibghat.vape_shop.mappers.category;

import com.sibghat.vape_shop.domains.Category;
import com.sibghat.vape_shop.dtos.category.AddCategoryDto;
import com.sibghat.vape_shop.mappers.IMapper;
import org.springframework.stereotype.Component;

@Component
public class AddCategoryDtoToCategoryMapper implements IMapper<AddCategoryDto, Category> {
    @Override
    public Category mapFrom(AddCategoryDto addCategoryDto) {
        return Category.builder()
                .name(addCategoryDto.getName())
                .build();
    }
}
