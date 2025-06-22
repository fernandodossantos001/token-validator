package com.itau.token.validator.domain.token.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.itau.token.validator.domain.token.ITokenParse;
import com.itau.token.validator.domain.token.ITokenService;
import com.itau.token.validator.domain.token.model.TokenDTO;
import com.itau.token.validator.domain.token.model.TokenValidateDTO;
import com.itau.token.validator.domain.validador.exception.ValidadorException;
import com.itau.token.validator.domain.validador.model.ValidadorDTO;
import com.itau.token.validator.domain.validador.service.IValidadorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TokenServiceImpl implements ITokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
    private final ITokenParse iTokenParse;
    private final IValidadorService iValidadorService;

    public TokenServiceImpl(ITokenParse iTokenParse, IValidadorService iValidadorService) {
        this.iTokenParse = iTokenParse;
        this.iValidadorService = iValidadorService;
    }

    @Override
    public TokenValidateDTO valida(TokenDTO tokenDTO) {
        JsonNode jsonNode = iTokenParse.parseTokenPayload(tokenDTO);
        List<ValidadorDTO> validadorDTO = new ArrayList<>();

        jsonNode.forEachEntry((k,v) ->{
            ValidadorDTO validar = iValidadorService.validar(v.asText(), k);
            if(validar.error()){
                validadorDTO.add(validar);
            }
        });

        if(!validadorDTO.isEmpty()){
            logger.warn("Erros ao executar validação token jwt {} ", validadorDTO);
            return new TokenValidateDTO(false);
        }

        return new TokenValidateDTO(true);
    }
}
