package com.itau.token.validator.domain.global.service.exception;

public class BaseUncheckedException extends RuntimeException{
    public BaseUncheckedException(String message) {
        super(message);
    }
}
