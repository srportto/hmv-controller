package br.com.hmv.repositories;

import br.com.hmv.models.entities.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findFuncionarioByIdFuncionario(String idFuncionario);

    void deleteByIdFuncionario(String idFuncionario);

    Page<Funcionario> findFuncionarioByCodigoGrupoFuncao(Long codigoGrupoFuncao, Pageable pageable);

    Funcionario findFuncionarioByEmail(String email);

    Funcionario findFuncionarioByCpf(String cpf);
}
