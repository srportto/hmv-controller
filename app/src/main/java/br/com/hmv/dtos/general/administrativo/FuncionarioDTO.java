package br.com.hmv.dtos.general.administrativo;

import br.com.hmv.models.entities.Especialidade;
import br.com.hmv.models.entities.Funcionario;
import br.com.hmv.models.enums.GrupoFuncaoFuncionarioEnum;
import br.com.hmv.models.enums.StatusFuncionarioEnum;
import br.com.hmv.models.mappers.FuncionarioMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("id_funcionario")
    private String idFuncionario;

    @JsonProperty("email")
    private String email;

    @JsonProperty("senha")
    private String senha;

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("crm")
    private String crm;

    @JsonProperty("primeiro_nome")
    private String primeiroNome;

    @JsonProperty("nome_completo")
    private String nomeCompleto;

    @JsonProperty("data_nascimento")
    private LocalDate dataNascimento;

    private EnderecoAdministrativoDTO endereco;

    @JsonProperty("grupo_funcao")
    private GrupoFuncaoFuncionarioEnum grupoFuncaoFuncionario;

    @JsonProperty("status")
    private StatusFuncionarioEnum statusFuncionario;

    @JsonProperty("telefone")
    private TelefoneDTO telefone;

    private Set<EspecialidadeAdministrativaDTO> especialidades = new HashSet<>();

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    //? construtor diferenciado - de entity Hospital + set<?> da entity Especialidade
    public FuncionarioDTO(Funcionario entity, Set<Especialidade> especialidades) {
        var dtoDefalt = FuncionarioMapper.INSTANCE.deFuncionarioParaDto(entity);

        especialidades.forEach(especialidadeItem -> this.especialidades.add(new EspecialidadeAdministrativaDTO(especialidadeItem)));
    }
}
