package org.ecommerce.system.domain.request.user.Voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherCreateRequest {
    private String name;
    private String code;
    private Integer type;
    private Float value;
    private Integer quantity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
}