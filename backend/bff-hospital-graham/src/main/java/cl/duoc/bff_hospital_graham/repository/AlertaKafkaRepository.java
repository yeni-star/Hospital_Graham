package cl.duoc.bff_hospital_graham.repository;

import cl.duoc.bff_hospital_graham.model.AlertaKafka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaKafkaRepository extends JpaRepository<AlertaKafka, Long> {

    List<AlertaKafka> findTop10ByOrderByIdAlertaDesc();
}