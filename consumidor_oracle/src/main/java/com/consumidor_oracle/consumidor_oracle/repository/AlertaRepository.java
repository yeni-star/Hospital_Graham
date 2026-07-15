package com.consumidor_oracle.consumidor_oracle.repository;

import com.consumidor_oracle.consumidor_oracle.model.AlertaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<AlertaMedica, Long> {

}