package org.ecommerce.system.application.service;

import org.ecommerce.system.domain.common.ResponseCommon;
import org.ecommerce.system.domain.dto.CategoryDto;
import org.ecommerce.system.domain.request.user.Category.CreateCategoryRequest;
import org.ecommerce.system.domain.request.user.Category.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CreateCategoryRequest request);

    CategoryDto updateCategory(UpdateCategoryRequest updateCategoryRequest);

    void deleteCategory(Long id);

    ResponseCommon<CategoryDto> getCategoryById(Long id);

    ResponseCommon<List<CategoryDto>> getAllCategories();

//    ResponseCommon<List<Tuple>> getTopCategoriesByProductCount();

}
