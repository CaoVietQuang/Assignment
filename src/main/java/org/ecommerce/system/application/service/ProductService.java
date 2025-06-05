package org.ecommerce.system.application.service;

import org.ecommerce.system.domain.common.PageResponse;
import org.ecommerce.system.domain.common.ResponseCommon;
import org.ecommerce.system.domain.dto.ProductDto;
import org.ecommerce.system.domain.dto.TopRatedProductDto;
import org.ecommerce.system.domain.entity.ProductEntity;
import org.ecommerce.system.domain.request.user.Product.CreateProductRequest;
import org.ecommerce.system.domain.request.user.Product.UpdateProductRequest;
import org.ecommerce.system.domain.response.user.Product.ProductResponse;

import java.util.List;

public interface ProductService {

    PageResponse<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDirection);

    ProductDto createProduct(CreateProductRequest request);

    ResponseCommon<ProductDto> getProductById(Long id);

    ProductDto updateProduct(UpdateProductRequest request);

    PageResponse<ProductResponse> getProductsByPriceRange(Double minPrice, Double maxPrice, int page, int size);

    PageResponse<ProductResponse> getProductsByPublisher(Long publisherId, int page, int size);

    void deleteProduct(Long id);

    ResponseCommon<List<TopRatedProductDto>> getTopRatedProducts();
}
