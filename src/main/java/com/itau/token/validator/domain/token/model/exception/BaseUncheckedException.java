package com.itau.token.validator.domain.token.model.exception;

public class BaseUncheckedException extends RuntimeException{
    public BaseUncheckedException(String message) {
        super(message);
    }
}
