package br.com.hmv.dtos.general.administrativo;

import br.com.hmv.models.entities.Telefone;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelefoneDTO implements Serializable {
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
    public TelefoneDTO(Telefone entity) {
        codigoPais = entity.getCodigoPais();
        codigoArea = entity.getCodigoArea();
        numero = entity.getNumero();
        descricao = entity.getDescricao();
    }
}
