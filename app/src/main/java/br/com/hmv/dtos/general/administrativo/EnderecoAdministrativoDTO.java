package br.com.hmv.dtos.general.administrativo;

import br.com.hmv.models.entities.Endereco;
import br.com.hmv.models.entities.Hospital;
import br.com.hmv.models.enums.StatusUnidadeHospitalEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    public EnderecoAdministrativoDTO(Endereco entity) {

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
