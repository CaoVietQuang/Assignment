package org.ecommerce.system.domain.enums;

public enum StatusVoucher {
    ACTIVE(0, "Đang hoạt động"),
    IN_ACTIVE(1, "Không hoạt động"),
    EXPIRED(2, "Đã hết hạn"),
    UP_COMING(3, "Sắp diễn ra"),
    CANCELLED(4, "Đã hủy");

    private final Integer value;
    private final String description;

    StatusVoucher(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static StatusVoucher fromValue(Integer value) {
        if (value == null) return null;
        for (StatusVoucher status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }

//    public static String getName(Integer value) {
//        if (value == null) return "";
//        for (StatusVoucher status : values()) {
//            if (status.getValue().equals(value)) {
//                return status.name();
//            }
//        }
//        return "";
//    }
}
