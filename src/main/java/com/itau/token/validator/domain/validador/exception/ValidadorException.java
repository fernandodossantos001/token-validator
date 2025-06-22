package com.itau.token.validator.domain.validador.exception;

import com.itau.token.validator.domain.global.service.exception.BaseUncheckedException;
import com.itau.token.validator.domain.global.service.exception.ValidationError;
import com.itau.token.validator.domain.validador.model.ValidadorDTO;

import java.util.List;

public class ValidadorException extends BaseUncheckedException {

    private List<ValidadorDTO> itensValidacao;

    public ValidadorException(String message, List<ValidadorDTO> itensValidacao) {
        super(message);
        this.itensValidacao = itensValidacao;
    }

    public ValidadorException(String message) {
        super(message);
    }
}
