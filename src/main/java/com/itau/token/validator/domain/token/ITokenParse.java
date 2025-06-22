package com.itau.token.validator.domain.token;

import com.fasterxml.jackson.databind.JsonNode;
import com.itau.token.validator.domain.token.model.TokenDTO;

public interface ITokenParse {
    JsonNode parseTokenPayload(TokenDTO tokenDTO);
}
