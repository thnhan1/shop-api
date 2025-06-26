package com.nhanab.shop.mapper;

import com.nhanab.shop.dto.CategoryResponse;
import com.nhanab.shop.dto.CreateCategoryRequest;
import com.nhanab.shop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CreateCategoryRequest categoryDto);

    CategoryResponse toDto(Category category);

    void updateFromDto(CreateCategoryRequest categoryDto, @MappingTarget Category category);
}
