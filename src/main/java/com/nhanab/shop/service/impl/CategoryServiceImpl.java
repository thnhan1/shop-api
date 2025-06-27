package com.nhanab.shop.service.impl;

import com.nhanab.shop.dto.CategoryResponse;
import com.nhanab.shop.dto.CreateCategoryRequest;
import com.nhanab.shop.exception.ResourceNotFoundException;
import com.nhanab.shop.mapper.CategoryMapper;
import com.nhanab.shop.model.Category;
import com.nhanab.shop.repository.CategoryRepository;
import com.nhanab.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse create(CreateCategoryRequest categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    public CategoryResponse getById(UUID id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("category id")));
    }

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    public void delete(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("category id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
