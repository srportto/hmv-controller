package br.com.hmv.dtos.responses.administrativo;

import br.com.hmv.dtos.general.administrativo.TelefoneDTO;
import br.com.hmv.models.entities.Telefone;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelefoneDefaultResponseDTO extends TelefoneDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("codigo_pais")
    private Integer codigoPais;

    @JsonProperty("codigo_area")
    private Integer codigoArea;

    @JsonProperty("numero")
    private Integer numero;

    @JsonProperty("descricao")
    private String descricao;

    //? construtor diferenciado - de entity para DTO
    public TelefoneDefaultResponseDTO(Telefone entity) {
        codigoPais = entity.getCodigoPais();
        codigoArea = entity.getCodigoArea();
        numero = entity.getNumero();
        descricao = entity.getDescricao();
    }
}
