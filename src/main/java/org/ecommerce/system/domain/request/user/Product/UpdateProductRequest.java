package org.ecommerce.system.domain.request.user.Product;

import lombok.Data;

import java.util.List;

@Data
public class UpdateProductRequest {
    private Long id;
    private String name;
    private String imageUrl;
    private Double price;
    private String code;
    private String color;
    private String size;
    private Double rating;
    private Integer isActive;
    private Long publisherId;
    private List<Long> categoryIds;
}
