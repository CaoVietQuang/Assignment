package org.ecommerce.system.domain.enums;

public enum OrderType {
    ONLINE(0, "Thanh toán trực tuyến"),
    OFFLINE(1, "Thanh toán bằng tiền mặt"),
    ;

    private final Integer value;
    private final String description;

    OrderType(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

//    public static String getName(Integer value) {
//        for (OrderType type : OrderType.values()) {
//            if (type.getValue().equals(value)) {
//                return type.name();
//            }
//        }
//        return null;
//    }

    public static OrderType fromValue(Integer value) {
        for (OrderType type : OrderType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
