package com.william.agendamento_transferencias_financeiras.repository;

import com.william.agendamento_transferencias_financeiras.entity.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
}
