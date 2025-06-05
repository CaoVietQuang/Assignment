package org.ecommerce.system.domain.enums;

public enum Gender {
    MALE(0, "Nam"),
    FEMALE(1, "Nữ"),
    OTHER(2, "Khác");

    private final Integer value;
    private final String description;

    Gender(Integer value, String description) {
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
//        if (value == null) return "";
//        for (Gender v : Gender.values()) {
//            if (v.getValue().equals(value)) {
//                return v.name();
//            }
//        }
//        return "";
//    }

    public static Gender fromValue(Integer value) {
        if (value == null) return null;
        for (Gender v : Gender.values()) {
            if (v.getValue().equals(value)) {
                return v;
            }
        }
        return null;
    }
}
