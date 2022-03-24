package br.com.hmv.models.mappers;

import br.com.hmv.dtos.request.emergencia.SintomaRequestDTO;
import br.com.hmv.dtos.responses.emergencia.SintomaDefaultResponseDTO;
import br.com.hmv.models.entities.Sintoma;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface SintomaMapper {
    SintomaMapper INSTANCE = Mappers.getMapper(SintomaMapper.class);

    Sintoma deDtoParaEntity(SintomaRequestDTO dto);

    SintomaDefaultResponseDTO deEntityParaDto(Sintoma entity);
}
