package br.com.hmv.dtos.general.administrativo;

import br.com.hmv.models.entities.ConvenioAdministrativo;
import br.com.hmv.models.enums.StatusConvenioEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvenioAtendidoAdministrativoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String descricao;

	@JsonProperty("status")
	private StatusConvenioEnum statusConvenio;

	private LocalDateTime dataCriacao;

	private LocalDateTime dataAtualizacao;

	//? construtor diferenciado - de entity para DTO
	public ConvenioAtendidoAdministrativoDTO(ConvenioAdministrativo convenioAdministrativo) {
		id = convenioAdministrativo.getId();
		descricao = convenioAdministrativo.getDescricao();
		statusConvenio = StatusConvenioEnum.obterStatusConvenio(convenioAdministrativo.getCodigoStatusConvenio());
		dataCriacao = convenioAdministrativo.getDataCriacao();
		dataAtualizacao = convenioAdministrativo.getDataAtualizacao();
	}
}
