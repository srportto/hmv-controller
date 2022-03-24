package br.com.hmv.models.mappers;


import br.com.hmv.dtos.request.administrativo.EnderecoInsertRequestDTO;
import br.com.hmv.dtos.responses.administrativo.EnderecoDefaultResponseDTO;
import br.com.hmv.models.entities.EnderecoAdministrativo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface EnderecoAdministrativoMapper {
    EnderecoAdministrativoMapper INSTANCE = Mappers.getMapper(EnderecoAdministrativoMapper.class);


    EnderecoAdministrativo deDtoParaEndereco(EnderecoInsertRequestDTO dto);

    EnderecoDefaultResponseDTO deEnderecoParaDto(EnderecoAdministrativo entity);
}
