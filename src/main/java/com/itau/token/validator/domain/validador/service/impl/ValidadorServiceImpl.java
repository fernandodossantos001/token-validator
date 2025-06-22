package com.itau.token.validator.domain.validador.service.impl;

import com.itau.token.validator.domain.global.service.MessageService;
import com.itau.token.validator.domain.token.model.ConstantsMessageException;
import com.itau.token.validator.domain.validador.service.IValidadorService;
import com.itau.token.validator.domain.validador.service.IValidadorStrategy;
import com.itau.token.validator.domain.validador.service.TipoValidadorEnum;
import com.itau.token.validator.domain.validador.model.ValidadorDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ValidadorServiceImpl implements IValidadorService {
    private final Map<TipoValidadorEnum, IValidadorStrategy> strategies;
    private final MessageService messageService;

    public ValidadorServiceImpl(List<IValidadorStrategy> strategies, MessageService messageService) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(IValidadorStrategy::getTipoValidador, strategy -> strategy));
        this.messageService = messageService;
    }

    @Override
    public ValidadorDTO validar(String payload,String tipoValidador){

        Optional<TipoValidadorEnum> tipoValidadorEnum = Arrays.stream(TipoValidadorEnum.values()).filter(t -> t.name().equalsIgnoreCase(tipoValidador))
                .findFirst();

        AtomicReference<ValidadorDTO> validadorDTO = new AtomicReference<>();
        tipoValidadorEnum
                .ifPresentOrElse(tipoValidadorEncontrado -> validadorDTO.set(strategies.get(tipoValidadorEncontrado).validar(payload)),
                () -> validadorDTO.set(new ValidadorDTO(Optional.of(messageService.getMessage(ConstantsMessageException.QUANTIDADE_CLAIM_INVALIDA.name())),true)));

        return validadorDTO.get();
    }

}
