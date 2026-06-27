package com.william.agendamento_transferencias_financeiras.exceptions;

public class TaxaNaoAplicavelException extends RuntimeException{
    public TaxaNaoAplicavelException(String mensagem){
        super(mensagem);
    }
}
