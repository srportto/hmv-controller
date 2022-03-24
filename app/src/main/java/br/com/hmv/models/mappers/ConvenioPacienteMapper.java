package br.com.hmv.models.mappers;

import br.com.hmv.dtos.request.paciente.ConvenioRequestDTO;
import br.com.hmv.dtos.responses.paciente.ConvenioDefaultResponseDTO;
import br.com.hmv.models.entities.ConvenioPaciente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface ConvenioPacienteMapper {
    ConvenioPacienteMapper INSTANCE = Mappers.getMapper(ConvenioPacienteMapper.class);

    ConvenioPaciente deDtoParaEntity(ConvenioRequestDTO dto);

    ConvenioDefaultResponseDTO deEntityParaDto(ConvenioPaciente entity);
}
