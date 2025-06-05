package org.ecommerce.system.domain.response.user.Feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecommerce.system.domain.response.user.Product.ProductResponse;
import org.ecommerce.system.domain.response.user.authentication.UserResponse;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {
    private Long id;
    private String content;
    private Integer rating;
    private ProductResponse product;
    private UserResponse user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}