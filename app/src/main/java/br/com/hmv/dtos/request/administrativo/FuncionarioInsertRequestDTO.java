package br.com.hmv.dtos.request.administrativo;

import br.com.hmv.models.enums.GrupoFuncaoFuncionarioEnum;
import br.com.hmv.services.validation.administrativo.funcionario.criacao.FuncionarioInsertValid;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FuncionarioInsertValid
public class FuncionarioInsertRequestDTO {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Campo email deve ser preenchido")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Campo senha deve ser preenchido")
    @JsonProperty("senha")
    private String senha;

    @NotBlank(message = "Campo CPF deve ser preenchido")
    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("crm")
    private String crm;

    @NotBlank(message = "Campo primeiro_nome deve ser preenchido")
    @JsonProperty("primeiro_nome")
    private String primeiroNome;

    @NotBlank(message = "Campo nome_completo deve ser preenchido")
    @JsonProperty("nome_completo")
    private String nomeCompleto;

    @NotNull(message = "Campo data_nascimento deve ser preenchido")
    @JsonProperty("data_nascimento")
    private LocalDate dataNascimento;

    @Valid
    @NotNull(message = "endereco deve ser preenchido")
    private EnderecoInsertRequestDTO endereco;

    @NotNull(message = "Campo grupo_funcao deve ser preenchido")
    @JsonProperty("grupo_funcao")
    private GrupoFuncaoFuncionarioEnum grupoFuncaoFuncionario;

    @NotNull(message = "Campo telefone deve ser preenchido")
    @JsonProperty("telefone")
    private TelefoneInsertRequestDTO telefone;
}