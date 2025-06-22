package com.itau.token.validator.domain.validador.service.impl;


import com.itau.token.validator.domain.global.service.MessageService;
import com.itau.token.validator.domain.token.model.ConstantsMessageException;
import com.itau.token.validator.domain.validador.service.IValidadorStrategy;
import com.itau.token.validator.domain.validador.service.TipoValidadorEnum;
import com.itau.token.validator.domain.validador.model.RoleEnum;
import com.itau.token.validator.domain.validador.model.ValidadorDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RoleValidadorServiceImpl implements IValidadorStrategy {

    private final MessageService messageService;

    public RoleValidadorServiceImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public ValidadorDTO validar(String payload) {
        Optional<RoleEnum> role = Arrays.stream(RoleEnum.values()).filter(roleEnum -> roleEnum.name().equalsIgnoreCase(payload)).findFirst();
        AtomicReference<ValidadorDTO> validadorDTO = new AtomicReference<>();
        role.ifPresentOrElse(r -> validadorDTO.set(new ValidadorDTO(Optional.empty(),false)),
                ()-> validadorDTO.set(new ValidadorDTO(Optional.of(messageService.getMessage(ConstantsMessageException.REGRA_NEGOCIO_ROLE_INVALIDA.name())),true)));
        return validadorDTO.get();
    }

    @Override
    public TipoValidadorEnum getTipoValidador() {
        return TipoValidadorEnum.ROLE;
    }
}

