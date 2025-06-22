package com.itau.token.validator.domain.validador.service;

import com.itau.token.validator.domain.global.service.MessageService;
import com.itau.token.validator.domain.validador.model.ValidadorDTO;
import com.itau.token.validator.domain.validador.service.impl.NameValidadorServiceImpl;
import com.itau.token.validator.domain.validador.service.impl.RoleValidadorServiceImpl;
import com.itau.token.validator.domain.validador.service.impl.SeedValidadorServiceImpl;
import com.itau.token.validator.domain.validador.service.impl.ValidadorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ValidadorServiceImplTest {


    private ValidadorServiceImpl validadorService;

    @Mock
    private MessageService messageService;

    @Test
    void testValidarName(){
        List<IValidadorStrategy> strategies = new ArrayList<>();
        NameValidadorServiceImpl nameValidadorService = new NameValidadorServiceImpl(messageService);
        strategies.add(nameValidadorService);
        validadorService = new ValidadorServiceImpl(strategies,messageService);
        ValidadorDTO validadorDTO = validadorService.validar("Luiz Inacio Messias Bolsonaro", "name");

        Assertions.assertFalse(validadorDTO.error());
        Assertions.assertEquals(Optional.empty(),validadorDTO.erroMessage());
    }

    @Test
    void testValidarRole(){
        List<IValidadorStrategy> strategies = new ArrayList<>();
        RoleValidadorServiceImpl roleValidadorService = new RoleValidadorServiceImpl(messageService);
        strategies.add(roleValidadorService);
        validadorService = new ValidadorServiceImpl(strategies,messageService);
        ValidadorDTO validadorDTO = validadorService.validar("Admin", "role");

        Assertions.assertFalse(validadorDTO.error());
        Assertions.assertEquals(Optional.empty(),validadorDTO.erroMessage());
    }


    @Test
    void testValidarSeed(){
        List<IValidadorStrategy> strategies = new ArrayList<>();
        SeedValidadorServiceImpl seedValidadorService = new SeedValidadorServiceImpl(messageService);

        strategies.add(seedValidadorService);
        validadorService = new ValidadorServiceImpl(strategies,messageService);
        ValidadorDTO validadorDTO = validadorService.validar("7841", "seed");

        Assertions.assertFalse(validadorDTO.error());
        Assertions.assertEquals(Optional.empty(),validadorDTO.erroMessage());
    }


    @Test
    void testValidarComTipoValidadorInvalido(){
        List<IValidadorStrategy> strategies = new ArrayList<>();
        SeedValidadorServiceImpl seedValidadorService = new SeedValidadorServiceImpl(messageService);
        RoleValidadorServiceImpl roleValidadorService = new RoleValidadorServiceImpl(messageService);
        NameValidadorServiceImpl nameValidadorService = new NameValidadorServiceImpl(messageService);
        strategies.add(seedValidadorService);
        strategies.add(roleValidadorService);
        strategies.add(nameValidadorService);

        String message = "Deve conter apenas 3 claims (Name, Role e Seed)";

        Mockito.when(messageService.getMessage(Mockito.anyString())).thenReturn(message);

        validadorService = new ValidadorServiceImpl(strategies,messageService);
        ValidadorDTO validadorDTO = validadorService.validar("xpto", "validadorInvalido");

        Assertions.assertTrue(validadorDTO.error());
        Assertions.assertFalse(validadorDTO.erroMessage().isEmpty());
        validadorDTO.erroMessage().ifPresent(me -> Assertions.assertEquals(message,me));
    }

}
