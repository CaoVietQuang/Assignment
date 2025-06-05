package org.ecommerce.system.application.service;


import org.ecommerce.system.domain.common.PageResponse;
import org.ecommerce.system.domain.request.user.Order.OrderCreateRequest;
import org.ecommerce.system.domain.response.user.Order.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(Long userId, OrderCreateRequest request);
    OrderResponse getOrderById(Long orderId);
    PageResponse<OrderResponse> getOrdersByUserId(Long userId, int page, int size);
    PageResponse<OrderResponse> getAllOrders(int page, int size);
    OrderResponse updateOrderStatus(Long orderId, Integer status);
    void cancelOrder(Long orderId);
    PageResponse<OrderResponse> getOrdersByStatus(Integer status, int page, int size);
    OrderResponse getOrderByCode(String code);
}
