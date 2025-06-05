package org.ecommerce.system.domain.response.user.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecommerce.system.domain.response.user.Category.CategoryResponse;
import org.ecommerce.system.domain.response.user.Feedback.FeedbackResponse;
import org.ecommerce.system.domain.response.user.Publisher.PublisherResponse;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private Double price;
    private String code;
    private Double rating;
    private String color;
    private String size;
    private Integer isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CategoryResponse> categories;
    private PublisherResponse publisher;
    private List<FeedbackResponse> feedbacks;
    private Long feedbackCount;
}
