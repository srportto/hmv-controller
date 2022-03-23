package br.com.hmv.dtos.request.administrativo;

import br.com.hmv.services.validation.administrativo.especialidade.criacao.EspecialidadeInsertValid;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
@EspecialidadeInsertValid
public class EspecialidadeInsertRequestDTO {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Campo nome deve ser preenchido")
    private String nome;
}