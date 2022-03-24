package br.com.hmv.dtos.request.paciente;

import br.com.hmv.models.enums.StatusFuncionarioEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class FuncionarioAtualizaStatusRequestDTO {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Campo status deve ser preenchido")
    @JsonProperty("status")
    private StatusFuncionarioEnum statusFuncionario;
}