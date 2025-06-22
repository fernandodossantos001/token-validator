package com.itau.token.validator.domain.validador.service;

import com.itau.token.validator.domain.global.service.MessageService;
import com.itau.token.validator.domain.validador.model.ValidadorDTO;
import com.itau.token.validator.domain.validador.service.impl.SeedValidadorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SeedValidadorServiceTest {

    @InjectMocks
    private SeedValidadorServiceImpl seedValidadorService;

    @Mock
    private MessageService messageService;


    @Test
    void testValidarSuccess(){
        ValidadorDTO validadorDTO = seedValidadorService.validar("7841");
        Assertions.assertFalse(validadorDTO.error());
        Assertions.assertTrue(validadorDTO.erroMessage().isEmpty());
    }

    @Test
    void testValidarErroNumeroInvalido(){
        String mensagemErro = "A claim Seed deve ser um número primo.";
        Mockito.when(messageService.getMessage(Mockito.anyString())).thenReturn(mensagemErro);
        ValidadorDTO validadorDTO = seedValidadorService.validar("22");
        Assertions.assertTrue(validadorDTO.error());
        Assertions.assertFalse(validadorDTO.erroMessage().isEmpty());
        validadorDTO.erroMessage().ifPresent(me -> Assertions.assertEquals(mensagemErro,me));
    }

    @Test
    void testValidarErroString(){
        String mensagemErro = "A claim Seed deve ser numérico";
        Mockito.when(messageService.getMessage(Mockito.anyString())).thenReturn(mensagemErro);
        ValidadorDTO validadorDTO = seedValidadorService.validar("Xpto");
        Assertions.assertTrue(validadorDTO.error());
        Assertions.assertFalse(validadorDTO.erroMessage().isEmpty());
        validadorDTO.erroMessage().ifPresent(me -> Assertions.assertEquals(mensagemErro,me));
    }


    @Test
    void testTipoValidador(){
        TipoValidadorEnum tipoValidador = seedValidadorService.getTipoValidador();
        Assertions.assertEquals(TipoValidadorEnum.SEED,tipoValidador);
    }
}
