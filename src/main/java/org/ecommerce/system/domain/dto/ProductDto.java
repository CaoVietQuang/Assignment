package org.ecommerce.system.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;
import org.ecommerce.system.domain.entity.CategoryEntity;
import org.ecommerce.system.domain.entity.FeedbackEntity;
import org.ecommerce.system.domain.entity.PublisherEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductDto {
    private Long id;
    private String name;
    private String imageUrl;
    private Double price;
    private String code;
    private Double rating;
    private String color;
    private String size;
    private Integer isActive;

    private PublisherDto publisher;
    private List<CategoryDto> categories;
    private FeedbackStats feedbackStats;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedbackStats {
        private Long id;
        private String content;
        private Integer rating;
    }
}
