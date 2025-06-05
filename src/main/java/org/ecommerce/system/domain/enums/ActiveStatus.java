package org.ecommerce.system.domain.enums;

public enum ActiveStatus {
    ACTIVE(0, "Hoạt động"),
    INACTIVE(1, "Không hoạt động");

    private final int value;
    private final String description;

    ActiveStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static ActiveStatus fromValue(int value) {
        for (ActiveStatus status : ActiveStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }
}
