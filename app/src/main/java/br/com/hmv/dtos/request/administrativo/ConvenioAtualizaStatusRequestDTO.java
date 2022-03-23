package br.com.hmv.dtos.request.administrativo;

import br.com.hmv.models.enums.StatusConvenioEnum;
import br.com.hmv.services.validation.administrativo.convenio.atualizacao_status.ConvenioAtualizaStatusValid;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@ConvenioAtualizaStatusValid
public class ConvenioAtualizaStatusRequestDTO {
    private static final long serialVersionUID = 1L;

    @NotNull( message = "Campo status deve ser preenchido")
    @JsonProperty("status")
    private StatusConvenioEnum statusConvenioEnum;
}