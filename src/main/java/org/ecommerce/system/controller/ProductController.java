package org.ecommerce.system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ecommerce.system.application.service.ProductService;
import org.ecommerce.system.domain.common.PageResponse;
import org.ecommerce.system.domain.common.ResponseCommon;
import org.ecommerce.system.domain.dto.ProductDto;
import org.ecommerce.system.domain.dto.TopRatedProductDto;
import org.ecommerce.system.domain.entity.ProductEntity;
import org.ecommerce.system.domain.enums.ResponseCode;
import org.ecommerce.system.domain.request.user.Product.CreateProductRequest;
import org.ecommerce.system.domain.request.user.Product.UpdateProductRequest;
import org.ecommerce.system.domain.response.user.Product.ProductResponse;
import org.ecommerce.system.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public PageResponse<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDirection) {
        try {
            return productService.getAllProducts(page, size, sortBy, sortDirection);
        } catch (Exception e) {
            log.error("Error fetching all products: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch products", e);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseCommon<ProductDto> getProductById(@PathVariable Long id) {
        try {
            return productService.getProductById(id);
        } catch (Exception e) {
            log.error("Error fetching product by ID: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch product", e);
        }
    }

    @PostMapping("/create")
    public ResponseCommon<ProductDto> createProduct(@RequestBody CreateProductRequest request) {
        try {
            ProductDto productDto = productService.createProduct(request);
            return ResponseCommon.success(productDto);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while creating category: {}", e.getMessage(), e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseCommon<ProductDto> updateProduct(@RequestBody UpdateProductRequest request) {
        try {
            ProductDto productDto = productService.updateProduct(request);
            return ResponseCommon.success(productDto);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while updating product: {}", e.getMessage(), e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ResponseCommon<String>> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            ResponseCommon<String> response = ResponseCommon.success("Product deleted successfully");
            return ResponseEntity.ok(response);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while deleting product: {}", e.getMessage(), e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/priceRange")
    public PageResponse<ProductResponse> getProductsByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @RequestParam int page,
            @RequestParam int size) {
        try {
            return productService.getProductsByPriceRange(minPrice, maxPrice, page, size);
        } catch (Exception e) {
            log.error("Error fetching products by price range: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch products by price range", e);
        }
    }

    @GetMapping("/Products/{publisherId}")
    public PageResponse<ProductResponse> getProductsByPublisher(
            @PathVariable Long publisherId,
            @RequestParam int page,
            @RequestParam int size) {
        try {
            return productService.getProductsByPublisher(publisherId, page, size);
        } catch (Exception e) {
            log.error("Error fetching products by publisher: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch products by publisher", e);
        }
    }

    @GetMapping("/topRated")
    public ResponseCommon<List<TopRatedProductDto>> getTopRatedProducts() {
        try {
            return productService.getTopRatedProducts();
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while fetching top rated products: {}", e.getMessage(), e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
