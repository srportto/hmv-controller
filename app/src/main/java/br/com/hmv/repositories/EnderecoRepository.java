package br.com.hmv.repositories;

import br.com.hmv.models.entities.Endereco;
import br.com.hmv.models.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
