package com.itau.token.validator.domain.validador.service;

import com.itau.token.validator.domain.validador.model.ValidadorDTO;

public interface IValidadorService {
    ValidadorDTO validar(String payload, String tipoValidador);
}
