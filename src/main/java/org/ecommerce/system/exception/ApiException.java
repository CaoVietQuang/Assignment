package org.ecommerce.system.exception;

import lombok.Getter;
import org.ecommerce.system.domain.enums.ResponseCode;

@Getter
public class ApiException extends RuntimeException {
    private final ResponseCode responseCode;

    public ApiException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public ApiException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getMessage(), cause);
        this.responseCode = responseCode;
    }

    public int getCode() {
        return responseCode.getCode();
    }

    public String getMessage() {
        return responseCode.getMessage();
    }
}
