package com.itau.token.validator.adapter.handler;


import com.itau.token.validator.domain.token.model.exception.BaseUncheckedException;
import com.itau.token.validator.domain.token.model.exception.StandardError;
import com.itau.token.validator.domain.token.model.exception.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.util.Calendar;
import java.util.List;

@ControllerAdvice
public class ResourceExceptionHandler {


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> dataViolation(ConstraintViolationException constraintViolationException, HttpServletRequest request){
            StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(),constraintViolationException.getCause().getMessage(),
                    Calendar.getInstance());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<StandardError> dataViolation(ServerWebInputException serverWebInputException){

        List<FieldError> fieldErrors = ((WebExchangeBindException) serverWebInputException).getBindingResult().getFieldErrors();
        ValidationError validationViolation = new ValidationError(HttpStatus.BAD_REQUEST.value(),"Erro de validação !",Calendar.getInstance());

        for(FieldError fieldError : fieldErrors){
            validationViolation.addError(fieldError.getField(),fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationViolation);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationViolation(MethodArgumentNotValidException e
            , HttpServletRequest request){

            ValidationError validationViolation = new ValidationError(HttpStatus.BAD_REQUEST.value(),"Erro de validação !",Calendar.getInstance());

            for(FieldError fieldError : e.getBindingResult().getFieldErrors()){
                validationViolation.addError(fieldError.getField(),fieldError.getDefaultMessage());
            }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationViolation);
    }

    @ExceptionHandler(BaseUncheckedException.class)
    public ResponseEntity<StandardError>  businessValidation(BaseUncheckedException baseUncheckedException){
        StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(),baseUncheckedException.getMessage(),
                Calendar.getInstance());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError>  exception(Exception ex){
        StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(),ex.getMessage(),
                Calendar.getInstance());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }
}
