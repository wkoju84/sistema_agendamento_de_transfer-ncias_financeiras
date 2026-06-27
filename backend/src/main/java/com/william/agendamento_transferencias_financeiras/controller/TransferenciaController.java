package com.william.agendamento_transferencias_financeiras.controller;

import com.william.agendamento_transferencias_financeiras.dtos.TransferenciaRequest;
import com.william.agendamento_transferencias_financeiras.dtos.TransferenciaResponse;
import com.william.agendamento_transferencias_financeiras.service.TransferenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransferenciaResponse agendar(@RequestBody @Valid TransferenciaRequest request){
        return transferenciaService.agendarTransferencia(request);
    }

    @GetMapping
    public List<TransferenciaResponse> listar(){
        return transferenciaService.listarTodos();
    }

}
