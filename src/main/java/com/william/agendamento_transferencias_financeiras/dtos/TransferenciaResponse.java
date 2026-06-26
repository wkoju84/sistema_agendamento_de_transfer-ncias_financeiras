package com.william.agendamento_transferencias_financeiras.dtos;

import com.william.agendamento_transferencias_financeiras.entity.Transferencia;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferenciaResponse {

    private Long id;
    private String contaOrigem;
    private String contaDestino;
    private BigDecimal valor;
    private BigDecimal taxa;
    private LocalDate dataTransferencia;
    private LocalDate dataAgendamento;

    public TransferenciaResponse() {}

    public TransferenciaResponse(Transferencia transferencia) {
        this.id = transferencia.getId();
        this.contaOrigem = transferencia.getContaOrigem();
        this.contaDestino = transferencia.getContaDestino();
        this.valor = transferencia.getValor();
        this.taxa = transferencia.getTaxa();
        this.dataTransferencia = transferencia.getDataTransferencia();
        this.dataAgendamento = transferencia.getDataAgendamento();
    }

    public Long getId() {
        return id;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public LocalDate getDataTransferencia() {
        return dataTransferencia;
    }

    public LocalDate getDataAgendamento() {
        return dataAgendamento;
    }
}
