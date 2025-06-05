package org.ecommerce.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.FOUND)
public class UserDisabledException extends RuntimeException {
    public UserDisabledException(String msg) {
        super(msg);
    }
}
