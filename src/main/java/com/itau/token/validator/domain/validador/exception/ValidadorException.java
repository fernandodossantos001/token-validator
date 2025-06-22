package com.itau.token.validator.domain.validador.exception;

import com.itau.token.validator.domain.global.exception.BaseUncheckedException;
import com.itau.token.validator.domain.validador.model.ValidadorDTO;

import java.util.List;
import java.util.Map;

public class ValidadorException extends BaseUncheckedException {

    private Map<String,ValidadorDTO> itensValidacao;

    public ValidadorException(String message, Map<String, ValidadorDTO> itensValidacao) {
        super(message);
        this.itensValidacao = itensValidacao;
    }

    public Map<String, ValidadorDTO> getItensValidacao() {
        return itensValidacao;
    }
}
