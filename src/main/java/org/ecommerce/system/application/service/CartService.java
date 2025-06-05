package org.ecommerce.system.application.service;

import org.ecommerce.system.domain.request.user.CartItem.CartItemRequest;
import org.ecommerce.system.domain.response.user.Cart.CartResponse;

public interface CartService {
    CartResponse getCartByUserId(Long userId);

    CartResponse addToCart(Long userId, CartItemRequest request);

    CartResponse updateCartItem(Long userId, Long cartItemId, Integer quantity);

    void removeFromCart(Long userId, Long cartItemId);

    void clearCart(Long userId);
}