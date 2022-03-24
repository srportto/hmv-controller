package br.com.hmv.repositories;

import br.com.hmv.models.entities.ConvenioAdministrativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvenioAdministrativoRepository extends JpaRepository<ConvenioAdministrativo, Long> {

}
