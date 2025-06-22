package com.itau.token.validator.domain.validador.service;


import com.itau.token.validator.domain.global.service.MessageService;
import com.itau.token.validator.domain.validador.model.ValidadorDTO;
import com.itau.token.validator.domain.validador.service.impl.RoleValidadorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoleValidadorServiceTest {


    @InjectMocks
    private RoleValidadorServiceImpl roleValidadorService;

    @Mock
    private MessageService messageService;

    @Test
    void testValidarSuccess(){
        ValidadorDTO validadorDTO = roleValidadorService.validar("Admin");
        Assertions.assertFalse(validadorDTO.error());
        Assertions.assertTrue(validadorDTO.erroMessage().isEmpty());
    }

    @Test
    void testValidarError(){
        String mensagemErro = "A claim Role deve conter apenas 1 dos três valores (Admin, Member e External)";
        Mockito.when(messageService.getMessage(Mockito.anyString())).thenReturn(mensagemErro);
        ValidadorDTO validadorDTO = roleValidadorService.validar("Super Admin");
        Assertions.assertTrue(validadorDTO.error());
        Assertions.assertFalse(validadorDTO.erroMessage().isEmpty());
        validadorDTO.erroMessage().ifPresent(me -> Assertions.assertEquals(mensagemErro,me));
    }

    @Test
    void testTipoValidador(){
        TipoValidadorEnum tipoValidador = roleValidadorService.getTipoValidador();
        Assertions.assertEquals(TipoValidadorEnum.ROLE,tipoValidador);
    }

}
