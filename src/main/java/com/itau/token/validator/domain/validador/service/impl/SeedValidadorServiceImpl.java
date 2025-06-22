package com.itau.token.validator.domain.validador.service.impl;

import com.itau.token.validator.domain.global.service.MessageService;
import com.itau.token.validator.domain.token.model.ConstantsMessageException;
import com.itau.token.validator.domain.validador.service.IValidadorStrategy;
import com.itau.token.validator.domain.validador.service.TipoValidadorEnum;
import com.itau.token.validator.domain.validador.model.ValidadorDTO;
import org.apache.commons.math3.primes.Primes;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SeedValidadorServiceImpl implements IValidadorStrategy {

    private final MessageService messageService;
    private final String regex = "^[0-9]*$";
    private final Pattern pattern;

    public SeedValidadorServiceImpl(MessageService messageService) {
        pattern = Pattern.compile(regex);
        this.messageService = messageService;
    }

    @Override
    public ValidadorDTO validar(String payload) {
        Matcher matcher = pattern.matcher(payload);
        if(matcher.matches()){
            Integer numeroPrimo = Integer.valueOf(payload);
            if(Primes.isPrime(numeroPrimo)){
                return new ValidadorDTO(Optional.empty(),false);
            }else{
                return new ValidadorDTO(Optional.of(messageService.getMessage(ConstantsMessageException.REGRA_NEGOCIO_SEED_NUMERO_PRIMO.name())),true);
            }
        }else {
            return new ValidadorDTO(Optional.of(messageService.getMessage(ConstantsMessageException.REGRA_NEGOCIO_SEED_NUMERICO.name())),true);
        }


    }

    @Override
    public TipoValidadorEnum getTipoValidador() {
        return TipoValidadorEnum.SEED;
    }
}
