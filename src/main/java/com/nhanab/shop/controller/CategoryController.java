package com.nhanab.shop.controller;

import com.nhanab.shop.dto.CategoryResponse;
import com.nhanab.shop.dto.CreateCategoryRequest;
import com.nhanab.shop.dto.UpdateCategoryDto;
import com.nhanab.shop.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "Category management APIs")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Create a new category", description = "Create and return a new category")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Category created successfully"), @ApiResponse(responseCode = "400", description = "Invalid input data")})
    @PostMapping
    public CategoryResponse create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category creation request", required = true) @RequestBody CreateCategoryRequest categoryDto) {
        return categoryService.create(categoryDto);
    }

    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories")
    @ApiResponse(responseCode = "200", description = "List of categories fetched successfully")
    @GetMapping
    public List<CategoryResponse> getAll() {
        return categoryService.getAll();
    }

    @Operation(summary = "Get category by ID", description = "Retrieve category details by its UUID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Category fetched successfully"), @ApiResponse(responseCode = "404", description = "Category not found")})
    @GetMapping("/{id}")
    public CategoryResponse getById(@Parameter(description = "UUID of the category", required = true) @PathVariable UUID id) {
        return categoryService.getById(id);
    }

    @Operation(summary = "Delete category", description = "Delete a category by its UUID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Category deleted successfully"), @ApiResponse(responseCode = "404", description = "Category not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "UUID of the category to delete", required = true) @PathVariable UUID id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update category name", description = "Update name of category")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Category updated successfully"), @ApiResponse(responseCode = "404", description = "Category not found")})
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable(name = "id") UUID id, @RequestBody UpdateCategoryDto updateCategoryDto) {
        this.categoryService.update(id, updateCategoryDto);
        return ResponseEntity.ok().build();
    }
}