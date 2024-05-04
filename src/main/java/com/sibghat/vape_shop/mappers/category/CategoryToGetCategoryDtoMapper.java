package com.sibghat.vape_shop.mappers.category;

import com.sibghat.vape_shop.domains.Category;
import com.sibghat.vape_shop.dtos.category.GetCategoryDto;
import com.sibghat.vape_shop.mappers.IMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryToGetCategoryDtoMapper implements IMapper<Category, GetCategoryDto> {
    @Override
    public GetCategoryDto mapFrom(Category category) {
        return GetCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .createdAt(category.getCreatedAt())
                .lastModifiedBy(category.getLastModifiedBy())
                .lastModifiedAt(category.getLastModifiedAt())
                .createdBy(category.getCreatedBy())
                .build();
    }
}
