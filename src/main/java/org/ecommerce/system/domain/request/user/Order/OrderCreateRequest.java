package org.ecommerce.system.domain.request.user.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecommerce.system.domain.request.user.OrderItem.OrderItemRequest;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    private String note;
    private String address;
    private String phoneNumber;
    private Long voucherId;
    private List<OrderItemRequest> items;
}