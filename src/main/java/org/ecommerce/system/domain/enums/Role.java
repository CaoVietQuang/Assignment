package org.ecommerce.system.domain.enums;

public enum Role {
    ADMIN(0, "Quản trị viên"),
    CUSTOMER(1, "Khách hàng"),
    STAFF(2, "Nhân viên");

    private final Integer value;
    private final String description;

    Role(Integer value, String description) {
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
//        for (Role role : Role.values()) {
//            if (role.getValue().equals(value)) {
//                return role.name();
//            }
//        }
//        return "";
//    }

    public static Role fromValue(Integer value) {
        for (Role role : Role.values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        return null;
    }
}
