package org.ecommerce.system.domain.response.user.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecommerce.system.domain.response.user.CartItem.CartItemResponse;
import org.ecommerce.system.domain.response.user.authentication.UserResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long id;
    private UserResponse user;
    private List<CartItemResponse> cartItems;
    private Double totalAmount;
    private Integer totalItems;
}
