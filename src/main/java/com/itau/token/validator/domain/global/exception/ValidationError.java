package com.itau.token.validator.domain.global.exception;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ValidationError extends StandardError{
    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String mensagem, Calendar timeStamp) {
        super(status, mensagem, timeStamp);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName,String message) {
        errors.add(new FieldMessage(fieldName,message));
    }
}
