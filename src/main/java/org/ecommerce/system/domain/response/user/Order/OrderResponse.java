package org.ecommerce.system.domain.response.user.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecommerce.system.domain.response.user.OrderItem.OrderDetailResponse;
import org.ecommerce.system.domain.response.user.Voucher.VoucherResponse;
import org.ecommerce.system.domain.response.user.authentication.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String code;
    private Float originMoney;
    private Float reduceMoney;
    private Float totalMoney;
    private Float shippingMoney;
    private Long confirmationDate;
    private Long expectedDeliveryDate;
    private Long deliveryStartDate;
    private Long receivedDate;
    private Integer type;
    private Integer status;
    private String statusDescription;
    private String note;
    private List<OrderDetailResponse> orderDetails;
    private UserResponse user;
    private VoucherResponse voucher;
    private LocalDateTime createdAt;
}