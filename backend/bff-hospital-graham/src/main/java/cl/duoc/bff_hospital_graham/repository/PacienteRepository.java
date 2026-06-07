package cl.duoc.bff_hospital_graham.repository;

import cl.duoc.bff_hospital_graham.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}