package br.com.hmv.dtos.responses.administrativo;

import br.com.hmv.models.entities.ConvenioAdministrativo;
import br.com.hmv.models.enums.StatusConvenioEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ConvenioDefaultResponseDTO {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String descricao;

    @JsonProperty("status")
    private StatusConvenioEnum statusConvenio;

    @JsonProperty("data_criacao")
    private LocalDateTime dataCriacao;

    @JsonProperty("data_atualizacao")
    private LocalDateTime dataAtualizacao;

    //Construtor diferenciado
    public ConvenioDefaultResponseDTO(ConvenioAdministrativo entity) {
        id = entity.getId();
        descricao = entity.getDescricao();
        statusConvenio = StatusConvenioEnum.obterStatusConvenio(entity.getCodigoStatusConvenio());
        dataCriacao = entity.getDataCriacao();
        dataAtualizacao = entity.getDataAtualizacao();
    }
}
