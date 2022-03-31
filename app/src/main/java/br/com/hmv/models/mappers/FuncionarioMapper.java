package br.com.hmv.models.mappers;

import br.com.hmv.dtos.request.administrativo.FuncionarioInsertRequestDTO;
import br.com.hmv.dtos.responses.administrativo.FuncionarioDefaultResponseDTO;
import br.com.hmv.dtos.responses.administrativo.FuncionarioForListResponseDTO;
import br.com.hmv.models.entities.Funcionario;
import br.com.hmv.models.enums.GeneroPessoasEnum;
import br.com.hmv.models.enums.GrupoFuncaoFuncionarioEnum;
import br.com.hmv.models.enums.StatusFuncionarioEnum;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public abstract class FuncionarioMapper {
    public static final FuncionarioMapper INSTANCE = Mappers.getMapper(FuncionarioMapper.class);

    public abstract Funcionario deDtoParaFuncionario(FuncionarioInsertRequestDTO dto);

    @BeforeMapping
    protected void preparaAntesDeMapearEntityParaDtoDefault(Funcionario entity, @MappingTarget FuncionarioDefaultResponseDTO dto) {
        var especialidades = entity.getEspecialidades();
        especialidades.forEach(especialidadeItem -> dto.getEspecialidades().add(EspecialidadeMapper.INSTANCE.deEspecialidadeParaDto(especialidadeItem)));

        dto.setStatusFuncionario(StatusFuncionarioEnum.obterStatusFuncionario(entity.getCodigoStatusFuncionario()));
        dto.setGrupoFuncaoFuncionario(GrupoFuncaoFuncionarioEnum.obterGrupoFuncaoFuncionario(entity.getCodigoGrupoFuncao()));
    }

    public abstract FuncionarioDefaultResponseDTO deFuncionarioParaDto(Funcionario entity);


    @AfterMapping
    protected void ajustaDepoisDeMapearEntityParaDtoDefault(Funcionario entity, @MappingTarget FuncionarioDefaultResponseDTO dto) {
        dto.getEspecialidades().clear();
        var especialidades = entity.getEspecialidades();
        especialidades.forEach(especialidadeItem -> dto.getEspecialidades().add(EspecialidadeMapper.INSTANCE.deEspecialidadeParaDto(especialidadeItem)));
        dto.setGenero(GeneroPessoasEnum.obterGeneroPessoa(entity.getCodigoGeneroPessoa()));
    }


    @BeforeMapping
    protected void preparaAntesDeMapearEntityParaListDto(Funcionario entity, @MappingTarget FuncionarioForListResponseDTO dto) {
        dto.setStatusFuncionario(StatusFuncionarioEnum.obterStatusFuncionario(entity.getCodigoStatusFuncionario()));
        dto.setGrupoFuncaoFuncionario(GrupoFuncaoFuncionarioEnum.obterGrupoFuncaoFuncionario(entity.getCodigoGrupoFuncao()));
    }

    public abstract FuncionarioForListResponseDTO deEntityParaRespresentacaoEmLista(Funcionario entity);
}
