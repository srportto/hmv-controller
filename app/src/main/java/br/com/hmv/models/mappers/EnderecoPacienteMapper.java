package br.com.hmv.models.mappers;

import br.com.hmv.dtos.request.paciente.EnderecoInsertRequestDTO;
import br.com.hmv.dtos.responses.paciente.EnderecoDefaultResponseDTO;
import br.com.hmv.models.entities.EnderecoPaciente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface EnderecoPacienteMapper {
    EnderecoPacienteMapper INSTANCE = Mappers.getMapper(EnderecoPacienteMapper.class);


    EnderecoPaciente deDtoParaEndereco(EnderecoInsertRequestDTO dto);

    EnderecoDefaultResponseDTO deEnderecoParaDto(EnderecoPaciente entity);
}
