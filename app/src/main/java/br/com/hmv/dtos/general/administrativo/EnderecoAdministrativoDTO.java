package br.com.hmv.dtos.general.administrativo;

import br.com.hmv.models.entities.EnderecoAdministrativo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoAdministrativoDTO implements Serializable {
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

    //? construtor diferenciado - de entity para DTO
    public EnderecoAdministrativoDTO(EnderecoAdministrativo entity) {

        codigoEndereco = entity.getCodigoEndereco();
        descricao = entity.getDescricao();
        logradouro = entity.getLogradouro();
        numero = entity.getNumero();
        complemento = entity.getComplemento();
        cidade = entity.getCidade();
        uf = entity.getUf();
        cep = entity.getCep();
    }
}
