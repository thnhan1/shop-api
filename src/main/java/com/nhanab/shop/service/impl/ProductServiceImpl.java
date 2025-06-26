package com.nhanab.shop.service.impl;

import com.nhanab.shop.dto.product.update.UpdateProductVariantRequest;
import com.nhanab.shop.dto.product.create.CreateProductRequest;
import com.nhanab.shop.dto.payload.PagedResponse;
import com.nhanab.shop.dto.product.read.ProductDetailResponse;
import com.nhanab.shop.dto.product.read.ProductSummaryResponse;
import com.nhanab.shop.mapper.ProductMapper;
import com.nhanab.shop.mapper.ProductVariantMapper;
import com.nhanab.shop.model.Category;
import com.nhanab.shop.model.Product;
import com.nhanab.shop.model.ProductImage;
import com.nhanab.shop.model.ProductVariant;
import com.nhanab.shop.repository.CategoryRepository;
import com.nhanab.shop.repository.ProductRepository;
import com.nhanab.shop.repository.ProductVariantRepository;
import com.nhanab.shop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantMapper productVariantMapper;

    @Override
    @Transactional
    public ProductDetailResponse create(CreateProductRequest request) {
        Category category = findCategoryById(request.getCategoryId());
        Product product = productMapper.toEntity(request);
        setProductImages(request,product);
        product.setCategory(category);

        for (var variantDto : request.getVariants()) {
            ProductVariant v = productMapper.toVariantEntity(variantDto);
           v.setProduct(product);
            ProductImage img = new ProductImage();
            img.setProduct(product);
            img.setProductVariant(v);
            img.setUrl(variantDto.getImageUrl());

            v.setProductImage(img);
            product.getImages().add(img);
            product.getVariants().add(v);
        }

        product = productRepository.save(product);
        return productMapper.toDetailResponse(product);
    }

    public ProductDetailResponse getById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        return productMapper.toDetailResponse(product);
    }

    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

    public PagedResponse<ProductSummaryResponse> getSummarizedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductSummaryResponse> summaries = productPage.getContent()
                .stream()
                .map(productMapper::toSummaryResponse)
                .toList();
        return PagedResponse.<ProductSummaryResponse>builder()
                .content(summaries)
                .pageNumber(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .first(productPage.isFirst())
                .last(productPage.isLast())
                .empty(productPage.isEmpty())
                .build();
    }


    @Override
    public void updateProductVariant(UUID variantId, UpdateProductVariantRequest updateProductVariantDto) {
        ProductVariant productVariant = productVariantRepository.findById(variantId).orElseThrow(() -> new EntityNotFoundException("Product variant not found with ID: " + variantId));
        productVariantMapper.updateFromDto(updateProductVariantDto, productVariant);
        productVariantRepository.save(productVariant);
    }

    // === PRIVATE METHODS ===

    private Category findCategoryById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));
    }

    private void setProductVariants(CreateProductRequest request, Product product) {
        Set<ProductVariant> variants = request.getVariants().stream().map(variantReq -> {
            ProductVariant variant = productMapper.toVariantEntity(variantReq);
            variant.setProduct(product);
            return variant;
        }).collect(Collectors.toSet());
        product.setVariants(variants);
    }

    private void setProductImages(CreateProductRequest request, Product product) {
        Set<ProductImage> images = request.getImages().stream().map(imageReq -> {
            ProductImage image = productMapper.toImageEntity(imageReq);
            image.setProduct(product);
            return image;
        }).collect(Collectors.toSet());

        product.setImages(images);
    }
}
