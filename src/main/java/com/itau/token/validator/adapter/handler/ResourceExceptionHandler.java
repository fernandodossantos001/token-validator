package com.itau.token.validator.adapter.handler;


import com.itau.token.validator.domain.global.service.exception.BaseUncheckedException;
import com.itau.token.validator.domain.global.service.exception.StandardError;
import com.itau.token.validator.domain.global.service.exception.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ResourceExceptionHandler.class);

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<StandardError> dataViolation(ServerWebInputException serverWebInputException){

        List<FieldError> fieldErrors = ((WebExchangeBindException) serverWebInputException).getBindingResult().getFieldErrors();
        ValidationError validationViolation = new ValidationError(HttpStatus.BAD_REQUEST.value(),"Erro de validação !",Calendar.getInstance());

        for(FieldError fieldError : fieldErrors){
            validationViolation.addError(fieldError.getField(),fieldError.getDefaultMessage());
        }
        logger.error("Erro ServerWebInputException",serverWebInputException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationViolation);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationViolation(MethodArgumentNotValidException e
            , HttpServletRequest request){

            ValidationError validationViolation = new ValidationError(HttpStatus.BAD_REQUEST.value(),"Erro de validação !",Calendar.getInstance());

            for(FieldError fieldError : e.getBindingResult().getFieldErrors()){
                validationViolation.addError(fieldError.getField(),fieldError.getDefaultMessage());
            }

        logger.error("Erro MethodArgumentNotValidException",e);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationViolation);
    }

    @ExceptionHandler(BaseUncheckedException.class)
    public ResponseEntity<StandardError>  businessValidation(BaseUncheckedException baseUncheckedException){
        StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(),baseUncheckedException.getMessage(),
                Calendar.getInstance());

        logger.error("Erro BaseUncheckedException ",baseUncheckedException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError>  exception(Exception ex){
        StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(),ex.getMessage(),
                Calendar.getInstance());

        logger.error("Erro Generico - Exception",ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }
}
