package com.william.agendamento_transferencias_financeiras.service;

import com.william.agendamento_transferencias_financeiras.dtos.TransferenciaRequest;
import com.william.agendamento_transferencias_financeiras.dtos.TransferenciaResponse;
import com.william.agendamento_transferencias_financeiras.entity.Transferencia;
import com.william.agendamento_transferencias_financeiras.exceptions.TaxaNaoAplicavelException;
import com.william.agendamento_transferencias_financeiras.repository.TransferenciaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferenciaService {

    private final TransferenciaRepository repository;

    public TransferenciaService(TransferenciaRepository repository) {
        this.repository = repository;
    }

    public TransferenciaResponse agendarTransferencia(TransferenciaRequest request){
        long dias = ChronoUnit.DAYS.between(LocalDate.now(), request.getDataTransferencia());
        BigDecimal taxa = calcularTaxa(dias, request.getValor());

        Transferencia transferencia = new Transferencia(
                request.getContaOrigem(),
                request.getContaDestino(),
                request.getValor(),
                taxa,
                request.getDataTransferencia(),
                LocalDate.now()
        );

        Transferencia transferenciaSalva = repository.save(transferencia);
        return new TransferenciaResponse(transferenciaSalva);
    }

    public List<TransferenciaResponse> listarTodos(){
        return repository.findAll()
                .stream()
                .map(TransferenciaResponse::new)
                .collect(Collectors.toList());
    }

    private BigDecimal calcularTaxa(long dias, BigDecimal valor) {
        if (dias == 0) {
            BigDecimal percentual = valor.multiply(new BigDecimal("0.025"));
            return new BigDecimal("3.00").add(percentual).setScale(2, RoundingMode.HALF_UP);
        }
        if (dias >= 1 && dias <= 10) {
            return new BigDecimal("12.00");
        }
        if (dias >= 11 && dias <= 20) {
            return valor.multiply(new BigDecimal("0.082")).setScale(2, RoundingMode.HALF_UP);
        }
        if (dias >= 21 && dias <= 30) {
            return valor.multiply(new BigDecimal("0.069")).setScale(2, RoundingMode.HALF_UP);
        }
        if (dias >= 31 && dias <= 40) {
            return valor.multiply(new BigDecimal("0.047")).setScale(2, RoundingMode.HALF_UP);
        }
        if (dias >= 41 && dias <= 50) {
            return valor.multiply(new BigDecimal("0.017")).setScale(2, RoundingMode.HALF_UP);
        }
        throw new TaxaNaoAplicavelException("Não há taxa aplicável para o período informado");
    }
}
