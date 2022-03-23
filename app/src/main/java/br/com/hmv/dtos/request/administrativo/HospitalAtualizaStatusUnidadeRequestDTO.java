package br.com.hmv.dtos.request.administrativo;

import br.com.hmv.models.enums.StatusUnidadeHospitalEnum;
import br.com.hmv.services.validation.administrativo.hospital.atualizacao_status.HospitalAtualizaStatusValid;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@HospitalAtualizaStatusValid
public class HospitalAtualizaStatusUnidadeRequestDTO {
    private static final long serialVersionUID = 1L;

    @NotNull( message = "Campo status deve ser preenchido")
    @JsonProperty("status")
    private StatusUnidadeHospitalEnum statusUnidadeHospitalEnum;
}