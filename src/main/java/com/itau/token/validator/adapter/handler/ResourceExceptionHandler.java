package com.itau.token.validator.adapter.handler;


import com.itau.token.validator.domain.global.exception.BaseUncheckedException;
import com.itau.token.validator.domain.global.exception.StandardError;
import com.itau.token.validator.domain.global.exception.ValidationError;
import com.itau.token.validator.domain.validador.exception.ValidadorException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Calendar;

@ControllerAdvice
public class ResourceExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResourceExceptionHandler.class);

    @ExceptionHandler(ValidadorException.class)
    public ResponseEntity<StandardError> validadorException(ValidadorException validadorException){
        ValidationError validationError = new ValidationError(HttpStatus.BAD_REQUEST.value(),"Erro de validação do token JWT !",Calendar.getInstance());
        validadorException.getItensValidacao().forEach((key,value) -> {
            value.erroMessage().ifPresent(em -> validationError.addError(key,em));
        });

        logger.error("Erro ValidadorException ",validadorException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
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
