package org.ecommerce.system.domain.enums;

public enum OrderStatus {
    PENDING(0, "Đơn hàng đang được chuẩn bị."),
    WAIT_FOR_CONFIRMATION(1, "Đơn hàng đang chờ xác nhận."),
    WAIT_FOR_DELIVERY(2, "Đơn hàng sẵn sàng để giao."),
    DELIVERING(3, "Đơn hàng đang được giao."),
    COMPLETED(4, "Đơn hàng đã hoàn thành."),
    CANCELED(5, "Đơn hàng đã bị hủy.");

    private final Integer value;
    private final String description;

    OrderStatus(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static OrderStatus fromValue(Integer value) {
        for (OrderStatus status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
