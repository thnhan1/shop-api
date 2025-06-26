package com.nhanab.shop.controller;

import com.nhanab.shop.dto.CategoryResponse;
import com.nhanab.shop.dto.CreateCategoryRequest;
import com.nhanab.shop.service.impl.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponse create(@RequestBody CreateCategoryRequest categoryDto) {
        return categoryService
                .create(categoryDto);
    }

    @GetMapping
    public List<CategoryResponse> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable UUID id) {
        return categoryService.getById(id);
    }

}
