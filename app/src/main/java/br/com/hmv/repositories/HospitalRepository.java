package br.com.hmv.repositories;

import br.com.hmv.models.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    Optional<Hospital> findHospitalsByCodigoUnidade(String codigoUnidade);

    void deleteByCodigoUnidade(String codigoUnidade);
}
