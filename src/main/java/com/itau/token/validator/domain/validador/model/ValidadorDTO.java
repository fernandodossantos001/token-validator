package com.itau.token.validator.domain.validador.model;

import java.util.Optional;

public record ValidadorDTO(Optional<String> messageError, Boolean error) {
}
