package org.ecommerce.system.domain.response.user.Voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherResponse {
    private Long id;
    private String name;
    private String code;
    private Integer status;
    private Integer type;
    private String typeDescription;
    private Float value;
    private Integer quantity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String imageUrl;
    private String description;
    private Boolean isExpired;
    private Boolean isActive;
}
