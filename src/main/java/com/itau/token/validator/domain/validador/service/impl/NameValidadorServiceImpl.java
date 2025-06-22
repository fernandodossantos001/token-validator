package com.itau.token.validator.domain.validador.service.impl;

import com.itau.token.validator.domain.global.service.MessageService;
import com.itau.token.validator.domain.token.model.ConstantsMessageException;
import com.itau.token.validator.domain.validador.service.IValidadorStrategy;
import com.itau.token.validator.domain.validador.service.TipoValidadorEnum;
import com.itau.token.validator.domain.validador.model.ValidadorDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NameValidadorServiceImpl implements IValidadorStrategy {

    private final String regex = "^[a-zA-Z ]*$";
    private final Pattern pattern;
    private final MessageService messageService;
    public NameValidadorServiceImpl(MessageService messageService) {
        pattern = Pattern.compile(regex);
        this.messageService = messageService;
    }

    @Override
    public ValidadorDTO validar(String payload) {
        Matcher matcher = pattern.matcher(payload);
        if(matcher.matches()){
            if(payload.length() > 256){
                return new ValidadorDTO(Optional.of(messageService.getMessage(ConstantsMessageException.REGRA_NEGOCIO_TAMANHO_NAME_INVALIDO.name())),true);
            }
            return new ValidadorDTO(Optional.empty(),false);
        }
        return new ValidadorDTO(Optional.of(messageService.getMessage(ConstantsMessageException.REGRA_NEGOCIO_NAME_INVALIDO.name())),true);
    }

    @Override
    public TipoValidadorEnum getTipoValidador() {
        return TipoValidadorEnum.NAME;
    }
}
