package com.nhanab.shop.controller;

import com.nhanab.shop.dto.payload.PagedResponse;
import com.nhanab.shop.dto.product.BulkUpdateStockRequest;
import com.nhanab.shop.dto.product.update.UpdateProductVariantRequest;
import com.nhanab.shop.dto.product.create.CreateProductRequest;
import com.nhanab.shop.dto.product.read.ProductDetailResponse;
import com.nhanab.shop.dto.product.read.ProductSummaryResponse;
import com.nhanab.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@Tag(name = "Product", description = "Product REST API")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create new Product", description = "Create new product with at least on variant", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = CreateProductRequest.class))), responses = {@ApiResponse(responseCode = "201", description = "Create product successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDetailResponse.class))), @ApiResponse(responseCode = "400", description = "bad request body")})
    public ProductDetailResponse create(@RequestBody CreateProductRequest createProductRequest) {
        return this.productService.create(createProductRequest);
    }

    @Operation(summary = "Get a paginated list of summarized products", description = "Retrieves a list of product summaries with pagination support.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated product summaries", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping
    public PagedResponse<ProductSummaryResponse> getSummarizedProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "12") int size) {
        return productService.getSummarizedProducts(page, size);
    }

    @Operation(summary = "Get product details by id", description = "Get details of product", responses = {@ApiResponse(responseCode = "200", description = "Get product detail successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDetailResponse.class))),})
    @GetMapping("/{id}")
    public ProductDetailResponse getProduct(@PathVariable("id") String id) {
        return this.productService.getById(UUID.fromString(id));
    }

    @Operation(summary = "Delete product by id", description = "Get details of product", responses = {@ApiResponse(responseCode = "204", description = "Delete product successfully")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        this.productService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update Product", description = "Update product details", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = BulkUpdateStockRequest.class))))
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") UUID productVariantId,
                                         @RequestBody UpdateProductVariantRequest updateProductVariantDto) {
        this.productService.updateProductVariant(productVariantId, updateProductVariantDto);
        return ResponseEntity.ok("update product variant successfully");
    }


}
