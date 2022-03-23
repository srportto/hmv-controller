package br.com.hmv.models.mappers;

import br.com.hmv.dtos.general.administrativo.EspecialidadeAdministrativaDTO;
import br.com.hmv.models.entities.Especialidade;
import br.com.hmv.models.enums.StatusEspecialidadeEnum;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface EspecialidadeMapper {
    EspecialidadeMapper INSTANCE = Mappers.getMapper(EspecialidadeMapper.class);


    Especialidade deDtoParaEspecialidade(EspecialidadeAdministrativaDTO dto);

    @BeforeMapping
    default  void preparaAntesDeMapearEntityParaDto(Especialidade entity, @MappingTarget EspecialidadeAdministrativaDTO dto){
        dto.setStatusEspecialidade(StatusEspecialidadeEnum.obterStatusEspecialidade(entity.getCodigoStatusEspecialidade()));
    }

    EspecialidadeAdministrativaDTO deEspecialidadeParaDto(Especialidade entity);
}
