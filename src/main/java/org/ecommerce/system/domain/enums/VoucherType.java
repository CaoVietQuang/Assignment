package org.ecommerce.system.domain.enums;

public enum VoucherType {
    PERCENTAGE(0, "Phần trăm"),
    CASH(1, "Tiền mặt");

    private final Integer value;
    private final String description;

    VoucherType(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static VoucherType fromValue(Integer value) {
        if (value == null) return null;
        for (VoucherType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }

//    public static String getName(Integer value) {
//        if (value == null) return "";
//        for (VoucherType type : values()) {
//            if (type.getValue().equals(value)) {
//                return type.name();
//            }
//        }
//        return "";
//    }
}
