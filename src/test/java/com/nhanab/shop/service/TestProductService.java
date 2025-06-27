package com.nhanab.shop.service;

import com.nhanab.shop.dto.product.create.CreateProductRequest;
import com.nhanab.shop.dto.product.read.ProductDetailResponse;
import com.nhanab.shop.dto.product.update.UpdateProductVariantRequest;
import com.nhanab.shop.mapper.ProductMapper;
import com.nhanab.shop.mapper.ProductVariantMapper;
import com.nhanab.shop.model.Category;
import com.nhanab.shop.model.Product;
import com.nhanab.shop.model.ProductVariant;
import com.nhanab.shop.repository.CategoryRepository;
import com.nhanab.shop.repository.ProductRepository;
import com.nhanab.shop.repository.ProductVariantRepository;
import com.nhanab.shop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductVariantRepository productVariantRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductVariantMapper productVariantMapper;

    private ProductServiceImpl productService;

    @BeforeEach
    void setup() {
        productService = new ProductServiceImpl(productRepository, categoryRepository, productMapper, productVariantRepository, productVariantMapper);
    }

    @Test
    void shouldReturnProductDetailResponse_whenProductExists() {
        UUID productId = UUID.randomUUID();

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setName("Test Product");

        ProductDetailResponse response = new ProductDetailResponse();
        response.setName("Test Product");

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        Mockito.when(productMapper.toDetailResponse(mockProduct)).thenReturn(response);

        ProductDetailResponse result = productService.getById(productId);

        assertEquals("Test Product", result.getName());
        verify(productRepository).findById(productId);
        verify(productMapper).toDetailResponse(mockProduct);
    }

    @Test
    void shouldCreateProductSuccessfully() {
        UUID categoryId = UUID.randomUUID();
        CreateProductRequest request = new CreateProductRequest();
        request.setCategoryId(categoryId);
        request.setName("New Product");

        request.setImages(new ArrayList<>());

        request.setVariants(new ArrayList<>());

        Category category = new Category();
        category.setId(categoryId);

        Product productEntity = new Product();
        productEntity.setName("New Product");

        ProductDetailResponse expectedResponse = new ProductDetailResponse();
        expectedResponse.setName("New Product");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productMapper.toEntity(request)).thenReturn(productEntity);
        when(productRepository.save(any(Product.class))).thenReturn(productEntity);
        when(productMapper.toDetailResponse(productEntity)).thenReturn(expectedResponse);

        ProductDetailResponse result = productService.create(request);

        assertEquals("New Product", result.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldUpdateProductVariant() {
        UUID variantId = UUID.randomUUID();
        UpdateProductVariantRequest request = new UpdateProductVariantRequest();
        request.setPrice(BigDecimal.valueOf(100));

        ProductVariant variant = new ProductVariant();
        variant.setId(variantId);

        when(productVariantRepository.findById(variantId)).thenReturn(Optional.of(variant));

        productService.updateProductVariant(variantId, request);

        verify(productVariantMapper).updateFromDto(request, variant);
        verify(productVariantRepository).save(variant);
    }


}
