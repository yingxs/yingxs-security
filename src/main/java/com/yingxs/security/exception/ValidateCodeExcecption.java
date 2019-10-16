package com.yingxs.security.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeExcecption extends AuthenticationException {

    public ValidateCodeExcecption(String msg) {
        super(msg);
    }
}
