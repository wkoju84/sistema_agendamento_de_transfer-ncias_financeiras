package com.william.agendamento_transferencias_financeiras.service;

import com.william.agendamento_transferencias_financeiras.dtos.TransferenciaRequest;
import com.william.agendamento_transferencias_financeiras.dtos.TransferenciaResponse;
import com.william.agendamento_transferencias_financeiras.entity.Transferencia;
import com.william.agendamento_transferencias_financeiras.exceptions.TaxaNaoAplicavelException;
import com.william.agendamento_transferencias_financeiras.repository.TransferenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferenciaServiceTest {

    @Mock
    private TransferenciaRepository repository;

    @InjectMocks
    private TransferenciaService service;

    private TransferenciaRequest request;

    @BeforeEach
    void setup(){
        request = new TransferenciaRequest();
        request.setContaOrigem("0012345678");
        request.setContaDestino("9876543210");
        request.setValor(new BigDecimal("1000.00"));
    }

    @Test
    void deveCalcularTaxaParaDiaZero(){
        request.setDataTransferencia(LocalDate.now());
        mockSave();

        TransferenciaResponse response = service.agendarTransferencia(request);
        assertEquals(new BigDecimal("28.00"), response.getTaxa());
    }

    @Test
    void deveCalcularTaxaParaEntre1E10Dias(){
        request.setDataTransferencia(LocalDate.now().plusDays(5));
        mockSave();

        TransferenciaResponse response = service.agendarTransferencia(request);
        assertEquals(new BigDecimal("12.00"), response.getTaxa());
    }

    @Test
    void deveCalcularTaxaParaLimiteInferiorDe1Dia() {
        request.setDataTransferencia(LocalDate.now().plusDays(1));
        mockSave();

        TransferenciaResponse response = service.agendarTransferencia(request);

        assertEquals(new BigDecimal("12.00"), response.getTaxa());
    }

    @Test
    void deveCalcularTaxaParaLimiteSuperiorDe10Dias() {
        request.setDataTransferencia(LocalDate.now().plusDays(10));
        mockSave();

        TransferenciaResponse response = service.agendarTransferencia(request);

        assertEquals(new BigDecimal("12.00"), response.getTaxa());
    }

    @Test
    void deveCalcularTaxaParaEntre11E20Dias() {
        request.setDataTransferencia(LocalDate.now().plusDays(15));
        mockSave();

        TransferenciaResponse response = service.agendarTransferencia(request);

        assertEquals(new BigDecimal("82.00"), response.getTaxa());
    }

    @Test
    void deveCalcularTaxaParaEntre21E30Dias() {
        request.setDataTransferencia(LocalDate.now().plusDays(25));
        mockSave();

        TransferenciaResponse response = service.agendarTransferencia(request);

        assertEquals(new BigDecimal("69.00"), response.getTaxa());
    }

    @Test
    void deveCalcularTaxaParaEntre31E40Dias() {
        request.setDataTransferencia(LocalDate.now().plusDays(35));
        mockSave();

        TransferenciaResponse response = service.agendarTransferencia(request);

        assertEquals(new BigDecimal("47.00"), response.getTaxa());
    }

    @Test
    void deveCalcularTaxaParaEntre41E50Dias() {
        request.setDataTransferencia(LocalDate.now().plusDays(45));
        mockSave();

        TransferenciaResponse response = service.agendarTransferencia(request);

        assertEquals(new BigDecimal("17.00"), response.getTaxa());
    }

    @Test
    void deveCalcularTaxaParaLimiteSuperiorDe50Dias() {
        request.setDataTransferencia(LocalDate.now().plusDays(50));
        mockSave();

        TransferenciaResponse response = service.agendarTransferencia(request);

        assertEquals(new BigDecimal("17.00"), response.getTaxa());
    }

    @Test
    void deveLancarExcecaoParaMaisDe50Dias() {
        request.setDataTransferencia(LocalDate.now().plusDays(51));

        assertThrows(TaxaNaoAplicavelException.class, () -> service.agendarTransferencia(request));
    }

    private void mockSave(){
        when(repository.save(any(Transferencia.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }
}
