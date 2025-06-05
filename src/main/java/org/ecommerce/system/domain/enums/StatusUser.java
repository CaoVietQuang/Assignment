package org.ecommerce.system.domain.enums;

public enum StatusUser {
    ACTIVE(0, "Hoạt động"),
    INACTIVE(1, "Không hoạt động"),
    IN_PROCESS(2, "Chờ xác thực");

    private final Integer value;
    private final String description;

    StatusUser(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
//trả về tên của enum theo giá trị ví dụ: ACTIVE
//    public static String getName(Integer value) {
//        if (value == null) return "";
//        for (StatusUser status : StatusUser.values()) {
//            if (status.getValue().equals(value)) {
//                return status.name();
//            }
//        }
//        return "";
//    }

    public static StatusUser fromValue(Integer value) {
        if (value == null) return null;
        for (StatusUser status : StatusUser.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
