package com.itau.token.validator.domain.token.model.exception;

import java.io.Serializable;
import java.util.Calendar;

public class StandardError implements Serializable {

        private Integer status;
        private String mensagem;
        private Calendar timeStamp;

    public StandardError(Integer status, String mensagem, Calendar timeStamp) {
        this.status = status;
        this.mensagem = mensagem;
        this.timeStamp = timeStamp;
    }

    public StandardError() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Calendar getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Calendar timeStamp) {
        this.timeStamp = timeStamp;
    }
}
