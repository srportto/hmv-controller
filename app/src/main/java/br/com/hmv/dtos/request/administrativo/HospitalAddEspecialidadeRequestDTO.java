package br.com.hmv.dtos.request.administrativo;

import br.com.hmv.services.validation.administrativo.hospital.add_especialidade.HospitalAddEspecialidadeValid;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@HospitalAddEspecialidadeValid
public class HospitalAddEspecialidadeRequestDTO {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Campo id_especialidade deve ser preenchido")
    @JsonProperty("id_especialidade")
    private Long idEspecialidade;
}