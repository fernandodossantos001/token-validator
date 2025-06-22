package com.itau.token.validator.domain.validador.service;


import com.itau.token.validator.domain.global.service.MessageService;
import com.itau.token.validator.domain.token.model.ConstantsMessageException;
import com.itau.token.validator.domain.validador.model.ValidadorDTO;
import com.itau.token.validator.domain.validador.service.impl.NameValidadorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class NameValidadorServiceTest {

    @InjectMocks
    private NameValidadorServiceImpl nameValidadorService;

    @Mock
    private MessageService messageService;

    @Test
    void testValidateSuccess(){
        ValidadorDTO validadorDTO = nameValidadorService.validar("Toninho Araujo");
        Assertions.assertFalse(validadorDTO.error());
        Assertions.assertEquals(Optional.empty(),validadorDTO.messageError());
    }

    @Test
    void testValidateError(){
        String mensagemErro = "A claim Name não pode ter carácter de números";
        Mockito.when(messageService.getMessage(Mockito.anyString())).thenReturn(mensagemErro);
        ValidadorDTO validadorDTO = nameValidadorService.validar("M4ria Olivia");
        Assertions.assertTrue(validadorDTO.error());
        Assertions.assertFalse(validadorDTO.messageError().isEmpty());
        validadorDTO.messageError().ifPresent( me -> Assertions.assertEquals(mensagemErro,me));
    }

    @Test
    void testValidateErrorNameSize(){
        String mensagemErro = "O tamanho máximo da claim Name é de 256 caracteres.";
        Mockito.when(messageService.getMessage(ConstantsMessageException.REGRA_NEGOCIO_TAMANHO_NAME_INVALIDO.name())).thenReturn(mensagemErro);
        ValidadorDTO validadorDTO = nameValidadorService.validar("Maximiliano Antonio Benedito Herculano Gabriel Fernando Domingos Jose Leopoldo Carlos Humberto Valentim Ricardo Luis Frederico Augusto Raimundo Vicente Felipe Teodoro Cristovao Roberto Manuel Henrique Santiago Lucas Alexandre Mauricio Eduardo Rafael Guilherme Otavio Silveira Monteiro Cardoso Albuquerque Sobrinho Neto Junior");
        Assertions.assertTrue(validadorDTO.error());
        Assertions.assertFalse(validadorDTO.messageError().isEmpty());
        validadorDTO.messageError().ifPresent( me -> Assertions.assertEquals(mensagemErro,me));
    }

    @Test
    void testTipoValidador(){
        TipoValidadorEnum tipoValidador = nameValidadorService.getTipoValidador();
        Assertions.assertNotNull(tipoValidador);
        Assertions.assertEquals(TipoValidadorEnum.NAME,tipoValidador);
    }
}
