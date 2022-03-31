package br.com.hmv.models.mappers;

import br.com.hmv.dtos.request.paciente.PacienteInsertRequestDTO;
import br.com.hmv.dtos.responses.paciente.PacienteDefaultResponseDTO;
import br.com.hmv.dtos.responses.paciente.PacienteForListResponseDTO;
import br.com.hmv.dtos.responses.paciente.PacienteInsertResponseDTO;
import br.com.hmv.models.entities.Paciente;
import br.com.hmv.models.enums.CadastroPacienteEnum;
import br.com.hmv.models.enums.GeneroPessoasEnum;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public abstract class PacienteMapper {
    public static final PacienteMapper INSTANCE = Mappers.getMapper(PacienteMapper.class);

    public abstract Paciente deDtoParaEntity(PacienteInsertRequestDTO dto);

    @BeforeMapping
    protected void preparaAntesDeMapearEntityParaDtoInsert(Paciente entity, @MappingTarget PacienteInsertResponseDTO dto) {
        dto.setIndicadorCadastro(CadastroPacienteEnum.obterStatusCadastroPaciente(entity.getIndicadorTipoCadastroRealizado()));
    }

    public abstract PacienteInsertResponseDTO deEntityParaDtoInsert(Paciente entity);


    public abstract PacienteDefaultResponseDTO deEntityParaDtoDefault(Paciente entity);


    @AfterMapping
    protected void ajustaDepoisDeMapearEntityParaDtoDefault(Paciente entity, @MappingTarget PacienteDefaultResponseDTO dto) {
        dto.setIndicadorCadastro(CadastroPacienteEnum.obterStatusCadastroPaciente(entity.getIndicadorTipoCadastroRealizado()));
        dto.setGenero(GeneroPessoasEnum.obterGeneroPessoa(entity.getCodigoGeneroPessoa()));

    }

    public abstract PacienteForListResponseDTO deEntityParaRespresentacaoEmLista(Paciente entity);

}
