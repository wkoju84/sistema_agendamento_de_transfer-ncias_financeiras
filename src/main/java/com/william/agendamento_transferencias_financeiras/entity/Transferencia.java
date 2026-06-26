package com.william.agendamento_transferencias_financeiras.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transferencias")
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String contaOrigem;

    @Column(nullable = false, length = 10)
    private String contaDestino;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal taxa;

    @Column(nullable = false)
    private LocalDate dataTransferencia;

    @Column(nullable = false)
    private LocalDate dataAgendamento;

    public Transferencia() {
    }

    public Transferencia(Long id, String contaOrigem, String contaDestino, BigDecimal valor,
                         BigDecimal taxa, LocalDate dataTransferencia, LocalDate dataAgendamento) {
        this.id = id;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.taxa = taxa;
        this.dataTransferencia = dataTransferencia;
        this.dataAgendamento = dataAgendamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    public LocalDate getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(LocalDate dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    public LocalDate getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDate dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }
}
