package org.ecommerce.system.domain.request.user.Category;

import lombok.Data;

@Data
public class CreateCategoryRequest {
    private String name;
    private String description;
}
