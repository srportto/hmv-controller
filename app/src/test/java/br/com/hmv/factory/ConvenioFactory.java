package br.com.hmv.factory;

import br.com.hmv.dtos.responses.administrativo.ConvenioDefaultResponseDTO;
import br.com.hmv.models.entities.ConvenioAdministrativo;
import br.com.hmv.models.enums.StatusConvenioEnum;

import java.time.LocalDateTime;

public class ConvenioFactory {

    public static ConvenioAdministrativo createConvenio() {
        var convenio = ConvenioAdministrativo.builder()
                .id(1l)
                .descricao("Convenio Teste")
                .codigoStatusConvenio(StatusConvenioEnum.ATIVO.getCodigoStatusConvenio())
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        return convenio;
    }

    public static ConvenioDefaultResponseDTO createConvenioAdministrativoDTO() {
        var convenio = createConvenio();
        return new ConvenioDefaultResponseDTO(convenio);
    }
}
