package org.ecommerce.system.application.infrastructure;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class EntityProperties {

    public static final byte LENGTH_ID = 36;
    public static final byte LENGTH_NAME = 100;
    public static final int LENGTH_DESCRIPTION = 1000;
    public static final byte LENGTH_USERNAME = 20;
    public static final byte LENGTH_EMAIL = 100;
    public static final byte LENGTH_PASSWORD = 100;
    public static final byte LENGTH_CODE = 20;
    public static final byte LENGTH_PHONE = 10;
    public static final int EXPIRATION = 5;
    public static final int MAX_ORDER_PENDING = 5;
    public static final int CODE_GET = 200;
    public static final int CODE_POST = 201;
    public static final String SUCCESS = "Thành công!";

    public static final String REFRESH_TOKEN = "refresh_token:";
    public static final String REFRESH_MAPPING = "refresh_mapping:";
    public static final String BLACK_LIST = "blacklist:";

    public static final Long DELIVERY_TIME_IN_MILLIS = 24L * 60 * 60 * 1000;

}
