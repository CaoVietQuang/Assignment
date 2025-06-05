package org.ecommerce.system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ecommerce.system.application.service.CategoryService;
import org.ecommerce.system.domain.common.ResponseCommon;
import org.ecommerce.system.domain.dto.CategoryDto;
import org.ecommerce.system.domain.entity.CategoryEntity;
import org.ecommerce.system.domain.enums.ResponseCode;
import org.ecommerce.system.domain.request.user.Category.CreateCategoryRequest;
import org.ecommerce.system.domain.request.user.Category.UpdateCategoryRequest;
import org.ecommerce.system.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseCommon<CategoryDto> createCategory(@RequestBody CreateCategoryRequest request) {
        try {
            CategoryDto category = categoryService.addCategory(request);
            return ResponseCommon.success(category);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while creating category: {}", e.getMessage(), e);
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseCommon<CategoryDto> updateCategory(@RequestBody UpdateCategoryRequest updateCategoryRequest, @PathVariable Long id) {
        try {
            if (updateCategoryRequest.getId() != null && !updateCategoryRequest.getId().equals(id)) {
                log.warn("ID mismatch: path={}, body={}", id, updateCategoryRequest.getId());
                throw new ApiException(ResponseCode.INVALID_INPUT);
            }
            updateCategoryRequest.setId(id);
            CategoryDto category = categoryService.updateCategory(updateCategoryRequest);
            return ResponseCommon.success(category);

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ResponseCommon<String>> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            ResponseCommon<String> response = ResponseCommon.success("Category deleted successfully");
            return ResponseEntity.ok(response);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseCommon<CategoryDto> getCategoryById(@PathVariable Long id) {
        try {
            ResponseCommon<CategoryDto> response = categoryService.getCategoryById(id);
            return response;
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllCategories")
    public ResponseCommon<List<CategoryDto>> getAllCategories() {
        try {
            ResponseCommon<List<CategoryDto>> response = categoryService.getAllCategories();
            return response;
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
