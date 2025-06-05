package org.ecommerce.system.domain.request.user.Product;

import lombok.Data;

import java.util.List;

@Data
public class CreateProductRequest {
    private String name;
    private String imageUrl;
    private Double price;
    private String code;
    private String color;
    private Double rating;
    private String size;
    private Long publisherId;
    private List<Long> categoryIds;
}
