package com.itau.token.validator.domain.token;

import com.itau.token.validator.domain.token.model.TokenDTO;
import com.itau.token.validator.domain.token.model.TokenValidateDTO;

public interface ITokenService {
    TokenValidateDTO valida(TokenDTO tokenDTO);
}
