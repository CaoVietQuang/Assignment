package org.ecommerce.system.application.service.Implements;

import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ecommerce.system.application.service.ProductService;
import org.ecommerce.system.domain.common.PageResponse;
import org.ecommerce.system.domain.common.ResponseCommon;
import org.ecommerce.system.domain.dto.CategoryDto;
import org.ecommerce.system.domain.dto.ProductDto;
import org.ecommerce.system.domain.dto.PublisherDto;
import org.ecommerce.system.domain.dto.TopRatedProductDto;
import org.ecommerce.system.domain.entity.CategoryEntity;
import org.ecommerce.system.domain.entity.ProductCategoryEntity;
import org.ecommerce.system.domain.entity.ProductEntity;
import org.ecommerce.system.domain.entity.PublisherEntity;
import org.ecommerce.system.domain.enums.OrderStatus;
import org.ecommerce.system.domain.enums.ResponseCode;
import org.ecommerce.system.domain.request.user.Product.CreateProductRequest;
import org.ecommerce.system.domain.request.user.Product.UpdateProductRequest;
import org.ecommerce.system.domain.response.user.Category.CategoryResponse;
import org.ecommerce.system.domain.response.user.Product.ProductResponse;
import org.ecommerce.system.domain.response.user.Publisher.PublisherResponse;
import org.ecommerce.system.exception.ApiException;
import org.ecommerce.system.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.security.PrivateKey;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public PageResponse<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDirection) {
        try {
            Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<ProductEntity> productPage = productRepository.findAll(pageable);

            List<ProductResponse> products = productPage.getContent().stream().map(product -> {
                ProductResponse response = new ProductResponse();

                response.setId(product.getId());
                response.setName(product.getName());
                response.setImageUrl(product.getImageUrl());
                response.setPrice(product.getPrice());
                response.setCode(product.getCode());
                response.setRating(product.getRating());
                response.setColor(product.getColor());
                response.setSize(product.getSize());
                response.setIsActive(product.getIsActive());
                response.setCreatedAt(product.getCreatedAt());
                response.setUpdatedAt(product.getUpdatedAt());

                // Map categories
                if (product.getProductCategories() != null) {
                    List<CategoryResponse> categories = product.getProductCategories().stream()
                            .map(pc -> {
                                CategoryResponse category = new CategoryResponse();
                                category.setId(pc.getCategory().getId());
                                category.setName(pc.getCategory().getName());
                                return category;
                            }).collect(Collectors.toList());
                    response.setCategories(categories);
                }

                // Map publisher
                if (product.getPublisher() != null) {
                    PublisherResponse publisher = new PublisherResponse();
                    publisher.setId(product.getPublisher().getId());
                    publisher.setName(product.getPublisher().getName());
                    response.setPublisher(publisher);
                }

                // Set feedback count
//                Long feedbackCount = feedbackRepository.countByProductId(product.getId());
//                response.setFeedbackCount(feedbackCount);

                return response;
            }).collect(Collectors.toList());

            return PageResponse.<ProductResponse>builder()
                    .currentPage(productPage.getNumber())
                    .pageSize(productPage.getSize())
                    .pagesCount(productPage.getTotalPages())
                    .currentTotalElementsCount(productPage.getNumberOfElements())
                    .hasNext(productPage.hasNext())
                    .hasPrevious(productPage.hasPrevious())
                    .content(products)
                    .build();


        } catch (Exception e) {
            log.error("Unexpected error while fetching all products: {}", e.getMessage(), e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ProductDto createProduct(CreateProductRequest request) {
        try {
            Optional<ProductEntity> existingProduct = productRepository.findByCode(request.getCode());
            if (existingProduct.isPresent()) {
                throw new ApiException(ResponseCode.PRODUCT_CODE_ALREADY_EXISTS);
            }

            PublisherEntity publisher = publisherRepository.findById(request.getPublisherId())
                    .orElseThrow(() -> {
                        log.warn("Publisher not found with id: {}", request.getPublisherId());
                        return new ApiException(ResponseCode.PUBLISHER_NOT_FOUND);
                    });

            if (publisher.getIsActive() != 0) {
                log.warn("Publisher is inactive: {}", request.getPublisherId());
                throw new ApiException(ResponseCode.PUBLISHER_INACTIVE);
            }

            ProductEntity product = new ProductEntity();
            product.setName(request.getName().trim());
            product.setImageUrl(request.getImageUrl() != null ? request.getImageUrl().trim() : null);
            product.setPrice(request.getPrice());
            product.setCode(request.getCode().trim());
            product.setColor(request.getColor() != null ? request.getColor().trim() : null);
            product.setSize(request.getSize() != null ? request.getSize().trim() : null);
            product.setPublisher(publisher);
            product.setIsActive(0);
            product.setRating(0.0);
            product.setProductCategories(Collections.emptyList());

            ProductEntity savedProduct = productRepository.save(product);

            List<Long> categoryIds = request.getCategoryIds();
            if (categoryIds != null && !categoryIds.isEmpty()) {
                List<ProductCategoryEntity> productCategories = new ArrayList<>();

                for (Long categoryId : categoryIds) {
                    CategoryEntity category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> {
                                log.warn("Category not found with id: {}", categoryId);
                                return new ApiException(ResponseCode.CATEGORY_NOT_FOUND);
                            });

                    if (category.getIsActive() != 0) {
                        log.warn("Category is inactive: {}", categoryId);
                        throw new ApiException(ResponseCode.CATEGORY_INACTIVE);
                    }

                    ProductCategoryEntity productCategory = new ProductCategoryEntity();
                    productCategory.setProduct(savedProduct);
                    productCategory.setCategory(category);
                    productCategoryRepository.save(productCategory);

                    productCategories.add(productCategory);
                }
                savedProduct.setProductCategories(productCategories);
            }
            return convertToDto(savedProduct);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while creating product: {}", e.getMessage(), e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    @Transactional
    public ResponseCommon<ProductDto> getProductById(Long id) {
        try {
            ProductEntity product = productRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ResponseCode.PRODUCT_NOT_FOUND));

            if (product.getIsActive() != 0) {
                log.warn("Product is inactive with ID: {}", id);
                throw new ApiException(ResponseCode.PRODUCT_INACTIVE);
            }

            return ResponseCommon.success(convertToDto(product));

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while fetching product by ID", e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    @Transactional
    public ProductDto updateProduct(UpdateProductRequest request) {
        try {
            ProductEntity product = productRepository.findById(request.getId())
                    .orElseThrow(() -> {
                        log.warn("Product not found with id: {}", request.getId());
                        return new ApiException(ResponseCode.PRODUCT_NOT_FOUND);
                    });
            if (request.getCode() != null && !request.getCode().equals(product.getCode())) {
                Optional<ProductEntity> existingProduct = productRepository.findByCode(request.getCode());
                if (existingProduct.isPresent() && !existingProduct.get().getId().equals(product.getId())) {
                    log.warn("Product code already exists: {}", request.getCode());
                    throw new ApiException(ResponseCode.PRODUCT_CODE_ALREADY_EXISTS);
                }
            }
            if (request.getName() != null && !request.getName().trim().isEmpty()) {
                product.setName(request.getName().trim());
            }
            if (request.getImageUrl() != null) {
                product.setImageUrl(request.getImageUrl().trim());
            }
            if (request.getPrice() != null) {
                if (request.getPrice() < 0) {
                    throw new ApiException(ResponseCode.INVALID_PRICE);
                }
                product.setPrice(request.getPrice());
            }
            if (request.getCode() != null && !request.getCode().trim().isEmpty()) {
                product.setCode(request.getCode().trim());
            }
            if (request.getColor() != null) {
                product.setColor(request.getColor().trim());
            }
            if (request.getSize() != null) {
                product.setSize(request.getSize().trim());
            }
            if (request.getPublisherId() != null) {
                if (product.getPublisher() == null || !product.getPublisher().getId().equals(request.getPublisherId())) {
                    PublisherEntity publisher = publisherRepository.findById(request.getPublisherId())
                            .orElseThrow(() -> {
                                log.warn("Publisher not found with id: {}", request.getPublisherId());
                                return new ApiException(ResponseCode.PUBLISHER_NOT_FOUND);
                            });

                    if (publisher.getIsActive() != 0) {
                        log.warn("Publisher is inactive: {}", request.getPublisherId());
                        throw new ApiException(ResponseCode.PUBLISHER_INACTIVE);
                    }

                    product.setPublisher(publisher);
                }
            }

            if (request.getCategoryIds() != null) {
                log.info("Updating categories for product {}", product.getId());

                List<Long> requestIds = request.getCategoryIds();
                Set<Long> currentIds = product.getProductCategories() != null
                        ? product.getProductCategories().stream()
                        .map(pc -> pc.getCategory().getId())
                        .collect(Collectors.toSet())
                        : Set.of();

                boolean categoriesChanged = !currentIds.equals(new HashSet<>(requestIds));
                if (categoriesChanged) {
                    productCategoryRepository.deleteByProductId(product.getId());
                    product.getProductCategories().clear();

                    for (Long categoryId : requestIds) {
                        CategoryEntity category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new ApiException(ResponseCode.CATEGORY_NOT_FOUND));

                        if (category.getIsActive() != 0) {
                            throw new ApiException(ResponseCode.CATEGORY_INACTIVE);
                        }

                        ProductCategoryEntity pc = new ProductCategoryEntity();
                        pc.setProduct(product);
                        pc.setCategory(category);
                        product.getProductCategories().add(pc);
                    }

                    log.info("Updated {} categories for product {}", requestIds.size(), product.getId());
                } else {
                    log.info("Categories unchanged for product {}", product.getId());
                }
            }


            ProductEntity updatedProduct = productRepository.save(product);
            ProductEntity refreshedProduct = productRepository.findById(updatedProduct.getId())
                    .orElseThrow(() -> new ApiException(ResponseCode.PRODUCT_NOT_FOUND));

            log.info("Product updated successfully with id: {}", refreshedProduct.getId());
            return convertToDto(refreshedProduct);

        } catch (ApiException e) {
            log.error("API error while updating product: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while updating product: {}", e.getMessage(), e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PageResponse<ProductResponse> getProductsByPriceRange(Double minPrice, Double maxPrice, int page, int size) {
        try {
            if (minPrice != null && minPrice < 0) {
                throw new ApiException(ResponseCode.INVALID_PRICE);
            }

            if (maxPrice != null && maxPrice < 0) {
                throw new ApiException(ResponseCode.INVALID_PRICE);
            }

            if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
                throw new ApiException(ResponseCode.INVALID_PRICE_RANGE);
            }
            Pageable pageable = PageRequest.of(page, size, Sort.by("price").ascending());
            Page<ProductEntity> productPage;
            if (minPrice != null && maxPrice != null) {
                productPage = productRepository.findByPriceRange(minPrice, maxPrice, pageable);
            } else if (minPrice != null) {
                productPage = productRepository.findByPriceRangeWithNullMax(minPrice, pageable);
            } else if (maxPrice != null) {
                productPage = productRepository.findByPriceRangeWithNullMin(maxPrice, pageable);
            } else {
                throw new ApiException(ResponseCode.INVALID_PRICE_RANGE);
            }
            List<ProductResponse> products = productPage.getContent().stream()
                    .map(this::mapToProductResponse)
                    .collect(Collectors.toList());

            return PageResponse.<ProductResponse>builder()
                    .currentPage(productPage.getNumber())
                    .pageSize(productPage.getSize())
                    .pagesCount(productPage.getTotalPages())
                    .currentTotalElementsCount(productPage.getNumberOfElements())
                    .hasNext(productPage.hasNext())
                    .hasPrevious(productPage.hasPrevious())
                    .content(products)
                    .build();

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while fetching products by price range: {}", e.getMessage(), e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public PageResponse<ProductResponse> getProductsByPublisher(Long publisherId, int page, int size) {
        try {
            if (publisherId == null || publisherId <= 0) {
                throw new ApiException(ResponseCode.INVALID_INPUT);
            }
            PublisherEntity publisher = publisherRepository.findById(publisherId)
                    .orElseThrow(() -> new ApiException(ResponseCode.PUBLISHER_NOT_FOUND));

            if (publisher.getIsActive() != 0) {
                log.warn("Publisher is inactive with ID: {}", publisherId);
                return PageResponse.<ProductResponse>builder()
                        .currentPage(page)
                        .pageSize(size)
                        .pagesCount(0)
                        .currentTotalElementsCount(0)
                        .hasNext(false)
                        .hasPrevious(page > 0)
                        .content(Collections.emptyList())
                        .build();
            }

            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            Page<ProductEntity> productPage = productRepository.findByPublisherIdWithPagination(publisherId, pageable);
            List<ProductResponse> products = productPage.getContent().stream()
                    .map(this::mapToProductResponse)
                    .collect(Collectors.toList());

            return PageResponse.<ProductResponse>builder()
                    .currentPage(productPage.getNumber())
                    .pageSize(productPage.getSize())
                    .pagesCount(productPage.getTotalPages())
                    .currentTotalElementsCount(productPage.getNumberOfElements())
                    .hasNext(productPage.hasNext())
                    .hasPrevious(productPage.hasPrevious())
                    .content(products)
                    .build();

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while fetching products by publisher: {}", e.getMessage(), e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        try {
            ProductEntity product = productRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Không tìm thấy sản phẩm với ID: {}", id);
                        return new ApiException(ResponseCode.PRODUCT_NOT_FOUND);
                    });
            if (product.getIsActive() == 1) {
                log.warn("Product with ID {} is already deleted/inactive", id);
                throw new ApiException(ResponseCode.PRODUCT_ALREADY_DELETED);
            }
//            boolean hasActiveOrders = orderDetailRepository.existsByProductIdAndOrderStatusIn(
//                    id,
//                    Arrays.asList(
//                            OrderStatus.PENDING.getValue(),
//                            OrderStatus.WAIT_FOR_CONFIRMATION.getValue(),
//                            OrderStatus.WAIT_FOR_DELIVERY.getValue(),
//                            OrderStatus.DELIVERING.getValue()
//                    )
//            );
//            if (hasActiveOrders) {
//                log.warn("Không thể vô hiệu hóa sản phẩm {} - tồn tại trong đơn hàng đang xử lý", id);
//                throw new ApiException(ResponseCode.PRODUCT_HAS_ACTIVE_ORDERS);
//            }
            product.setIsActive(1);
            ProductEntity updatedProduct = productRepository.save(product);

        } catch (ApiException e) {
            log.error("API error while deleting product: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while deleting product: {}", e.getMessage(), e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseCommon<List<TopRatedProductDto>> getTopRatedProducts() {
        try {
            List<Tuple> tuples = productRepository.findTopRatedProducts();
            if (tuples.isEmpty()) {
                return ResponseCommon.success(Collections.emptyList());
            }

            List<TopRatedProductDto> dtos = tuples.stream()
                    .map(t -> new TopRatedProductDto(
                            t.get("productId", Long.class),
                            t.get("productName", String.class),
                            t.get("productCode", String.class),
                            t.get("price", Double.class),
                            t.get("rating", Double.class),
                            t.get("imageUrl", String.class),
                            t.get("publisherName", String.class),
                            t.get("feedbackCount", Long.class),
                            t.get("averageRating", Double.class)
                    ))
                    .toList();

            return ResponseCommon.success(dtos);

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    private ProductResponse mapToProductResponse(ProductEntity product) {
        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setImageUrl(product.getImageUrl());
        response.setPrice(product.getPrice());
        response.setCode(product.getCode());
        response.setRating(product.getRating());
        response.setColor(product.getColor());
        response.setSize(product.getSize());
        response.setIsActive(product.getIsActive());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        // Map categories
        if (product.getProductCategories() != null) {
            List<CategoryResponse> categories = product.getProductCategories().stream()
                    .map(pc -> {
                        CategoryResponse category = new CategoryResponse();
                        category.setId(pc.getCategory().getId());
                        category.setName(pc.getCategory().getName());
                        return category;
                    }).collect(Collectors.toList());
            response.setCategories(categories);
        }

        // Map publisher
        if (product.getPublisher() != null) {
            PublisherResponse publisher = new PublisherResponse();
            publisher.setId(product.getPublisher().getId());
            publisher.setName(product.getPublisher().getName());
            response.setPublisher(publisher);
        }

        return response;
    }

    private ProductDto convertToDto(ProductEntity entity) {
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImageUrl(entity.getImageUrl());
        dto.setPrice(entity.getPrice());
        dto.setCode(entity.getCode());
        dto.setRating(entity.getRating());
        dto.setColor(entity.getColor());
        dto.setSize(entity.getSize());
        dto.setIsActive(entity.getIsActive());

        if (entity.getPublisher() != null) {
            PublisherDto publisherDto = new PublisherDto();
            publisherDto.setId(entity.getPublisher().getId());
            publisherDto.setName(entity.getPublisher().getName());
            publisherDto.setDescription(entity.getPublisher().getDescription());
            publisherDto.setIsActive(entity.getPublisher().getIsActive());
            dto.setPublisher(publisherDto);
        }

        if (entity.getProductCategories() != null) {
            List<CategoryDto> categories = entity.getProductCategories().stream()
                    .map(pc -> {
                        CategoryEntity category = pc.getCategory();
                        return new CategoryDto(
                                category.getId(),
                                category.getName(),
                                category.getDescription(),
                                category.getIsActive()
                        );
                    })
                    .toList();
            dto.setCategories(categories);
        }

        // Map feedback (nếu có)
        // dto.setFeedbackStats(...); // nếu cần xử lý feedback thực tế

        return dto;
    }

}
