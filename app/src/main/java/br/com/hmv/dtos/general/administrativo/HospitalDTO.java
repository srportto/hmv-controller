package br.com.hmv.dtos.general.administrativo;

import br.com.hmv.models.entities.Especialidade;
import br.com.hmv.models.entities.Hospital;
import br.com.hmv.models.enums.StatusUnidadeHospitalEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("codigo_unidade")
    private String codigoUnidade;

    @JsonProperty("nome_unidade")
    private String nomeUnidade;

    private EnderecoAdministrativoDTO endereco;

    @JsonProperty("status")
    private StatusUnidadeHospitalEnum statusUnidadeHospital;

    private List<EspecialidadeAdministrativaDTO> especialidades = new ArrayList<>();

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    //? construtor diferenciado - de entity para DTO
    public HospitalDTO(Hospital entity) {
        codigoUnidade = entity.getCodigoUnidade();
        nomeUnidade = entity.getNomeUnidade();
        endereco = new EnderecoAdministrativoDTO(entity.getEndereco());
        statusUnidadeHospital = StatusUnidadeHospitalEnum.obterStatusUnidadeHospital(entity.getCodigoStatusUnidade());
        dataCriacao = entity.getDataCriacao();
        dataAtualizacao = entity.getDataAtualizacao();
    }

    //? construtor diferenciado - de entity Hospital + set<?> da entity Especialidade
    public HospitalDTO(Hospital entity, Set<Especialidade> especialidades) {
        this(entity); // chamando construtor que recebe entity
        especialidades.forEach(especialidadeItem -> this.especialidades.add(new EspecialidadeAdministrativaDTO(especialidadeItem)));
    }
}
