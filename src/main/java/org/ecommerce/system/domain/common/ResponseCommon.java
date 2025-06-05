package org.ecommerce.system.domain.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ecommerce.system.domain.enums.ResponseCode;

@Getter
@Setter
@ToString
public class ResponseCommon<T> {
    private int code;
    private String message;
    private T data;

    public ResponseCommon() {
    }

    public ResponseCommon(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseCommon(ResponseCode responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    public static <T> ResponseCommon<T> success(T data) {
        return new ResponseCommon<>(ResponseCode.SUCCESS, data);
    }

    public static <T> ResponseCommon<T> error(ResponseCode responseCode) {
        return new ResponseCommon<>(responseCode, null);
    }

    public static <T> ResponseCommon<T> error(ResponseCode responseCode, T data) {
        return new ResponseCommon<>(responseCode, data);
    }
}