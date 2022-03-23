package br.com.hmv.dtos.request.administrativo;

import br.com.hmv.services.validation.administrativo.convenio.criacao.ConvenioInsertValid;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
@ConvenioInsertValid
public class ConvenioInsertRequestDTO {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Campo descricao deve ser preenchido")
    private String descricao;
}