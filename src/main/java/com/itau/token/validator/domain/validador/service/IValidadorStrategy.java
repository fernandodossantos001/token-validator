package com.itau.token.validator.domain.validador.service;

import com.itau.token.validator.domain.validador.model.ValidadorDTO;

public interface IValidadorStrategy {
    ValidadorDTO validar(String payload);
    TipoValidadorEnum getTipoValidador();
}
