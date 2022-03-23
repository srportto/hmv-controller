package br.com.hmv.dtos.request.administrativo;

import br.com.hmv.services.validation.administrativo.hospital.criacao.HospitalInsertValid;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@HospitalInsertValid
public class HospitalUnidadeInsertRequestDTO {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Campo nome_unidade deve ser preenchido")
    @JsonProperty("nome_unidade")
    private String nomeUnidade;

    @NotNull(message = "endereco deve ser preenchido")
    private EnderecoInsertRequestDTO endereco;
}