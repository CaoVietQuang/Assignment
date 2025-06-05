package org.ecommerce.system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TopRatedProductDto {
    private Long id;
    private String productName;
    private String productCodel;
    private Double pricel;
    private Double rating;
    private String imageUrl;
    private String publisherName;
    private Long feedbackCount;
    private Double averageRating;
}
