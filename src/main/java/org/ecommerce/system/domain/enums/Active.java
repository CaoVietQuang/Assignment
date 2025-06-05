package org.ecommerce.system.domain.enums;

public enum Active {
    ACTIVE(0, "Hoạt động"),
    INACTIVE(1, "Không hoạt động");

    private final int value;
    private final String description;

    Active(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static Active fromValue(int value) {
        for (Active status : Active.values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }
}
