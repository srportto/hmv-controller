package br.com.hmv.repositories;

import br.com.hmv.models.entities.EnderecoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoPacienteRepository extends JpaRepository<EnderecoPaciente, Long> {

}
