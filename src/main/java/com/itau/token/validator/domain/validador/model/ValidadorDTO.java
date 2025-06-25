package com.itau.token.validator.domain.validador.model;

import java.io.Serializable;
import java.util.Optional;

public record ValidadorDTO(Optional<String> erroMessage, Boolean error) implements Serializable {
}
