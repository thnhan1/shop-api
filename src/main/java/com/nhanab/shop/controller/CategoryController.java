package com.nhanab.shop.controller;

import com.nhanab.shop.dto.CategoryResponse;
import com.nhanab.shop.dto.CreateCategoryRequest;
import com.nhanab.shop.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryServiceImpl categoryServiceImpl;

    @PostMapping
    public CategoryResponse create(@RequestBody CreateCategoryRequest categoryDto) {
        return categoryServiceImpl
                .create(categoryDto);
    }

    @GetMapping
    public List<CategoryResponse> getAll() {
        return categoryServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable UUID id) {
        return categoryServiceImpl.getById(id);
    }

}
