package br.com.hmv.dtos.responses.administrativo;

import br.com.hmv.models.entities.Especialidade;
import br.com.hmv.models.enums.StatusEspecialidadeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EspecialidadeDefaultResponseDTO {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;

    @JsonProperty("status")
    private StatusEspecialidadeEnum statusEspecialidade;

    @JsonProperty("data_criacao")
    private LocalDateTime dataCriacao;

    @JsonProperty("data_atualizacao")
    private LocalDateTime dataAtualizacao;

    //? construtor diferenciado - de entity para DTO
    public EspecialidadeDefaultResponseDTO(Especialidade entity) {
        id = entity.getId();
        nome = entity.getNome();
        statusEspecialidade = StatusEspecialidadeEnum.obterStatusEspecialidade(entity.getCodigoStatusEspecialidade());
        dataCriacao = entity.getDataCriacao();
        dataAtualizacao = entity.getDataAtualizacao();
    }
}
