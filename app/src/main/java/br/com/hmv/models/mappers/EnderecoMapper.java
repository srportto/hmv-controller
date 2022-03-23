package br.com.hmv.models.mappers;

import br.com.hmv.dtos.request.administrativo.EnderecoInsertRequestDTO;
import br.com.hmv.dtos.responses.administrativo.EnderecoDefaultResponseDTO;
import br.com.hmv.models.entities.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface EnderecoMapper {
    EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);


    Endereco deDtoParaEndereco(EnderecoInsertRequestDTO dto);

    EnderecoDefaultResponseDTO deEnderecoParaDto(Endereco entity);
}
