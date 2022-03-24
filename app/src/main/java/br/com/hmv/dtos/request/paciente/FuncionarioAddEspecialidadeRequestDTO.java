package br.com.hmv.dtos.request.paciente;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class FuncionarioAddEspecialidadeRequestDTO {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Campo id_especialidade deve ser preenchido")
    @JsonProperty("id_especialidade")
    private Long idEspecialidade;
}