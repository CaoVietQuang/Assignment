package org.ecommerce.system.application.service.Implements;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ecommerce.system.application.service.CategoryService;
import org.ecommerce.system.domain.common.ResponseCommon;
import org.ecommerce.system.domain.dto.CategoryDto;
import org.ecommerce.system.domain.enums.ResponseCode;
import org.ecommerce.system.domain.request.user.Category.CreateCategoryRequest;
import org.ecommerce.system.domain.request.user.Category.UpdateCategoryRequest;
import org.ecommerce.system.domain.entity.CategoryEntity;
import org.ecommerce.system.exception.ApiException;
import org.ecommerce.system.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryDto addCategory(CreateCategoryRequest request) {
        try {
            Optional<CategoryEntity> existingCategory = categoryRepository.findByName(request.getName());
            if (existingCategory.isPresent()) {
                throw new ApiException(ResponseCode.CATEGORY_ALREADY_EXISTS);
            }
            CategoryEntity category = new CategoryEntity()
                    .setName(request.getName())
                    .setDescription(request.getDescription())
                    .setIsActive(0);
            CategoryEntity savedCategory = categoryRepository.save(category);
            return new CategoryDto(
                    savedCategory.getId(),
                    savedCategory.getName(),
                    savedCategory.getDescription(),
                    savedCategory.getIsActive()
            );
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(UpdateCategoryRequest updateCategoryRequest) {
        try {
            CategoryEntity existingCategory = categoryRepository.findById(updateCategoryRequest.getId())
                    .orElseThrow(() -> {
                        return new ApiException(ResponseCode.CATEGORY_NOT_FOUND);
                    });
            Optional<CategoryEntity> categoryWithSameName = categoryRepository.findByName(updateCategoryRequest.getName());
            if (categoryWithSameName.isPresent() && !categoryWithSameName.get().getId().equals(updateCategoryRequest.getId())) {
                throw new ApiException(ResponseCode.CATEGORY_ALREADY_EXISTS);
            }
            existingCategory.setName(updateCategoryRequest.getName());
            existingCategory.setDescription(updateCategoryRequest.getDescription());

            CategoryEntity updatedCategory = categoryRepository.save(existingCategory);
            return new CategoryDto(
                    updatedCategory.getId(),
                    updatedCategory.getName(),
                    updatedCategory.getDescription(),
                    updatedCategory.getIsActive()
            );
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        try {
            var category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ApiException(ResponseCode.CATEGORY_NOT_FOUND));

            if (category.getProductCategories() != null && !category.getProductCategories().isEmpty()) {
                var productCount = category.getProductCategories().size();
                log.warn("Cannot delete category {} - has {} associated products",
                        category.getId(), productCount);
                throw new ApiException(ResponseCode.CATEGORY_HAS_PRODUCTS);
            }
            category.setIsActive(1); // 1 Inactive
            categoryRepository.save(category);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseCommon<CategoryDto> getCategoryById(Long id) {
        try {
            CategoryEntity category = categoryRepository.findById(id)
                    .orElseThrow(() -> {
                        return new ApiException(ResponseCode.CATEGORY_NOT_FOUND);
                    });

            if (category.getIsActive() != 0) {
                throw new ApiException(ResponseCode.CATEGORY_NOT_FOUND);
            }

            return ResponseCommon.success(mapToDto(category));

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseCommon<List<CategoryDto>> getAllCategories() {
        try {
            var categories = categoryRepository.findByIsActive(0);
            if (categories.isEmpty()) {
                return ResponseCommon.error(ResponseCode.CATEGORY_NOT_FOUND);
            }

            List<CategoryDto> result = categories.stream()
                    .map(this::mapToDto)
                    .toList();

            return ResponseCommon.success(result);
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    private CategoryDto mapToDto(CategoryEntity category) {
        return new CategoryDto()
                .setId(category.getId())
                .setName(category.getName())
                .setDescription(category.getDescription())
                .setIsActive(category.getIsActive());
    }
}
