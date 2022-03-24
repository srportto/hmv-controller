package br.com.hmv.repositories;

import br.com.hmv.models.entities.EnderecoAdministrativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoAdministrativoRepository extends JpaRepository<EnderecoAdministrativo, Long> {

}
