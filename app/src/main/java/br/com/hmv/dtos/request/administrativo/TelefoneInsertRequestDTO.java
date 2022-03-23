package br.com.hmv.dtos.request.administrativo;

import br.com.hmv.services.validation.administrativo.convenio.criacao.ConvenioInsertValid;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@ConvenioInsertValid
public class TelefoneInsertRequestDTO {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Campo codigo_pais deve ser preenchido")
    @JsonProperty("codigo_pais")
    private Integer codigoPais;

    @NotNull(message = "Campo codigo_area deve ser preenchido")
    @JsonProperty("codigo_area")
    private Integer codigoArea;

    @NotNull(message = "Campo numero deve ser preenchido")
    @JsonProperty("numero")
    private Integer numero;

    @JsonProperty("descricao")
    private String descricao;
}