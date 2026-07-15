package com.consumidor_alertas_oracle.consumidor_alertas_oracle.repository;

import com.consumidor_alertas_oracle.consumidor_alertas_oracle.model.AlertaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaMedicaRepository extends JpaRepository<AlertaMedica, Long> {
}