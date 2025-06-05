package org.ecommerce.system.domain.request.user.Category;

import lombok.Data;

@Data
public class UpdateCategoryRequest {
    private Long id;
    private String name;
    private String description;
}
