package com.william.agendamento_transferencias_financeiras.dtos;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferenciaRequest {

    @NotNull(message = "Conta de origem é obrigatória")
    @Pattern(regexp = "\\d{10}", message = "Conta de origem deve conter 10 dígitos numéricos")
    private String contaOrigem;

    @NotNull
    @Pattern(regexp = "\\d{10}", message = "Conta de destino deve conter 10 dígitos numéricos")
    private String contaDestino;

    @NotNull
    @Positive(message = "O valor deve ser maior que zero")
    private BigDecimal valor;

    @NotNull
    @Future(message = "A data de transferência deve ser uma data futura")
    private LocalDate dataTransferencia;

    public TransferenciaRequest(){}

    public String getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(String contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(String contaDestino) {
        this.contaDestino = contaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(LocalDate dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }
}
