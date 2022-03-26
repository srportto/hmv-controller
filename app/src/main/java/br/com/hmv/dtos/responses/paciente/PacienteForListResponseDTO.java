package br.com.hmv.dtos.responses.paciente;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PacienteForListResponseDTO {

    @JsonProperty("id_paciente")
    private String idPaciente;

    @JsonProperty("primeiro_nome")
    private String primeiroNome;

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("email")
    private String email;

}