package com.nhanab.shop.service;

import com.nhanab.shop.dto.CategoryResponse;
import com.nhanab.shop.dto.CreateCategoryRequest;
import com.nhanab.shop.dto.UpdateCategoryDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponse create(CreateCategoryRequest categoryDto);

    CategoryResponse getById(UUID id);

    List<CategoryResponse> getAll();

    void update(UUID id, UpdateCategoryDto updateCategoryDto);

    void delete(UUID id);
}
