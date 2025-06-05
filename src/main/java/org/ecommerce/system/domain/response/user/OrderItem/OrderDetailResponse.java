package org.ecommerce.system.domain.response.user.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecommerce.system.domain.response.user.Product.ProductResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    private Long id;
    private ProductResponse product;
    private Integer quantity;
    private Float price;
    private Float totalPrice;
}
