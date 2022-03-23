package br.com.hmv.dtos.responses.administrativo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDefaultResponseDTO {
    private static final long serialVersionUID = 1L;

    @JsonProperty("codigo_endereco")
    private String codigoEndereco;

    private String descricao;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String cidade;
    private String uf;
    private Integer cep;
}
