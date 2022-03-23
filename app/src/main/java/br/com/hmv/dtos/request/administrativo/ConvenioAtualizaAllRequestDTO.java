package br.com.hmv.dtos.request.administrativo;

import br.com.hmv.models.enums.StatusConvenioEnum;
import br.com.hmv.services.validation.administrativo.convenio.atualizacao_all.ConvenioAtualizaAllValid;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@ConvenioAtualizaAllValid
public class ConvenioAtualizaAllRequestDTO {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Campo descricao deve ser preenchido")
    private String descricao;

    @NotNull(message = "Campo status deve ser preenchido")
    @JsonProperty("status")
    private StatusConvenioEnum statusConvenioEnum;
}