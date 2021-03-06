package br.com.hmv.dtos.responses.administrativo;

import br.com.hmv.dtos.general.administrativo.EspecialidadeAdministrativaDTO;
import br.com.hmv.dtos.general.administrativo.TelefoneDTO;
import br.com.hmv.models.enums.GrupoFuncaoFuncionarioEnum;
import br.com.hmv.models.enums.StatusFuncionarioEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuncionarioForListResponseDTO {
    private static final long serialVersionUID = 1L;

    @JsonProperty("id_funcionario")
    private String idFuncionario;

    @JsonProperty("email")
    private String email;

    @JsonIgnore
    private String senha;

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("crm")
    private String crm;

    @JsonIgnore
    private String primeiroNome;

    @JsonProperty("nome_completo")
    private String nomeCompleto;

    @JsonProperty("data_nascimento")
    private LocalDate dataNascimento;

    @JsonIgnore
    private EnderecoDefaultResponseDTO endereco;

    @JsonProperty("grupo_funcao")
    private GrupoFuncaoFuncionarioEnum grupoFuncaoFuncionario;

    @JsonProperty("status")
    private StatusFuncionarioEnum statusFuncionario;

    @JsonIgnore
    private TelefoneDTO telefone;

    @JsonIgnore
    private Set<EspecialidadeAdministrativaDTO> especialidades = new HashSet<>();
}