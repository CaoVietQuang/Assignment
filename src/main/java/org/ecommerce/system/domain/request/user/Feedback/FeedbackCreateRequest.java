package org.ecommerce.system.domain.request.user.Feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackCreateRequest {
    private String content;
    private Integer rating;
    private Long productId;
}
