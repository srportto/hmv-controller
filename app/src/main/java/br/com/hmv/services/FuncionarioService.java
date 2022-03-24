package br.com.hmv.services;

import br.com.hmv.dtos.request.administrativo.FuncionarioAddEspecialidadeRequestDTO;
import br.com.hmv.dtos.request.administrativo.FuncionarioAtualizaStatusRequestDTO;
import br.com.hmv.dtos.request.administrativo.FuncionarioInsertRequestDTO;
import br.com.hmv.dtos.request.administrativo.FuncionarioRemoveEspecialidadeRequestDTO;
import br.com.hmv.dtos.responses.administrativo.FuncionarioDefaultResponseDTO;
import br.com.hmv.dtos.responses.administrativo.FuncionarioForListResponseDTO;
import br.com.hmv.exceptions.DatabaseException;
import br.com.hmv.exceptions.ResourceNotFoundException;
import br.com.hmv.models.entities.Funcionario;
import br.com.hmv.models.entities.Role;
import br.com.hmv.models.enums.GrupoFuncaoFuncionarioEnum;
import br.com.hmv.models.enums.NivelPermissaoEnum;
import br.com.hmv.models.enums.StatusFuncionarioEnum;
import br.com.hmv.models.mappers.FuncionarioMapper;
import br.com.hmv.repositories.EspecialidadeRepository;
import br.com.hmv.repositories.FuncionarioRepository;
import br.com.hmv.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FuncionarioService {
    private static Logger logger = LoggerFactory.getLogger(FuncionarioService.class);
    private FuncionarioRepository funcionarioRepository;
    private EspecialidadeRepository especialidadeRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public FuncionarioDefaultResponseDTO criacao(FuncionarioInsertRequestDTO dto) {
        String logCode = "criacao(FuncionarioInsertRequestDTO)";
        logger.info("{} - solicitacao de inclusao {}", logCode, dto);

        var entity = dtoToEntityOnCreate(dto);
        entity = funcionarioRepository.save(entity);

        logger.info("{} - Convenio incluido com sucesso {}", logCode, entity);
        return entityToResponseDefault(entity);
    }

    @Transactional
    public FuncionarioDefaultResponseDTO updateStatus(String idFuncionario, FuncionarioAtualizaStatusRequestDTO dto) {
        String logCode = "updateStatus(String, FuncionarioAtualizaStatusRequestDTO)";
        logger.info("{} - solicitacao de atualizacao de status {}", logCode, dto);

        try {
            var objOptional = funcionarioRepository.findFuncionarioByIdFuncionario(idFuncionario);
            Funcionario entity = objOptional.orElseThrow(() -> new ResourceNotFoundException("recurso nao encontrado id: " + idFuncionario));

            //passa status novo
            entity.setCodigoStatusFuncionario(dto.getStatusFuncionario().getCodigoStatusFuncionario());
            entity = funcionarioRepository.save(entity);

            logger.info("{} - atualizacao realizada com sucesso {}", logCode, entity);
            return FuncionarioMapper.INSTANCE.deFuncionarioParaDto(entity);

        } catch (EntityNotFoundException e) {
            logger.warn("{} - recurso nao encontrado id: {} ", idFuncionario);
            throw new ResourceNotFoundException("Recurso nao encontrado id: " + idFuncionario);
        }
    }

    @Transactional
    public FuncionarioDefaultResponseDTO addEspecialidade(String idFuncionario, FuncionarioAddEspecialidadeRequestDTO dto) {
        String logCode = "addEspecialidade(String, HospitalAddEspecialidadeRequestDTO)";
        logger.info("{} - solicitacao de adicao de especialidade {}", logCode, dto);

        try {
            var objOptional = funcionarioRepository.findFuncionarioByIdFuncionario(idFuncionario);
            Funcionario entity = objOptional.orElseThrow(() -> new ResourceNotFoundException("recurso nao encontrado id: " + idFuncionario));

            var entityEspecialidade = especialidadeRepository.getOne(dto.getIdEspecialidade());

            //add especialidade
            entity.getEspecialidades().add(entityEspecialidade);
            entity = funcionarioRepository.save(entity);

            logger.info("{} - adicao de especialidade realizada com sucesso {}", logCode, entity);
            return FuncionarioMapper.INSTANCE.deFuncionarioParaDto(entity);

        } catch (EntityNotFoundException e) {
            logger.warn("{} - recurso nao encontrado id: {} ", logCode, idFuncionario);
            throw new ResourceNotFoundException("Recurso nao encontrado id: " + idFuncionario);

        } catch (Exception e) {
            logger.warn("{} - erro ao adicionar especialidade: {} ", logCode, e);
            throw new ResourceNotFoundException("Recurso nao encontrado id: " + idFuncionario);
        }
    }

    @Transactional
    public FuncionarioDefaultResponseDTO removeEspecialidade(String idFuncionario, FuncionarioRemoveEspecialidadeRequestDTO dto) {
        String logCode = "removeEspecialidade(String, FuncionarioRemoveEspecialidadeRequestDTO)";
        logger.info("{} - solicitacao de remocao de especialidade {}", logCode, dto);

        try {
            var objOptional = funcionarioRepository.findFuncionarioByIdFuncionario(idFuncionario);
            Funcionario entity = objOptional.orElseThrow(() -> new ResourceNotFoundException("recurso nao encontrado id: " + idFuncionario));

            var entityEspecialidade = especialidadeRepository.getOne(dto.getIdEspecialidade());

            //remove especialidade
            entity.getEspecialidades().remove(entityEspecialidade);
            entity = funcionarioRepository.save(entity);

            logger.info("{} - solicitacao de remocao de especialidade concluida com sucesso {}", logCode, entity);
            return FuncionarioMapper.INSTANCE.deFuncionarioParaDto(entity);
        } catch (EntityNotFoundException e) {
            logger.warn("{} - recurso nao encontrado id: {} ", logCode, idFuncionario);
            throw new ResourceNotFoundException("Recurso nao encontrado id: " + idFuncionario);

        } catch (Exception e) {
            logger.warn("{} - erro ao adicionar especialidade: {} ", logCode, e);
            throw new ResourceNotFoundException("Recurso nao encontrado id: " + idFuncionario);
        }
    }

    @Transactional
    public void delete(String idFuncionario) {
        String logCode = "delete(String)";
        logger.info("{} - deletando recurso: {}", logCode, idFuncionario);

        try {
            funcionarioRepository.deleteByIdFuncionario(idFuncionario);
            logger.info("{} - recurso deletado com sucesso: {}", logCode, idFuncionario);

        } catch (EmptyResultDataAccessException e) {
            logger.warn("{} - recurso nao encontrado: {}", logCode, idFuncionario);
            throw new ResourceNotFoundException("Recurso nao encontrado id: " + idFuncionario);

        } catch (DataIntegrityViolationException e) {
            logger.warn("{} - erro de integridade de dados: {}", logCode, idFuncionario);
            throw new DatabaseException("Integrity violation - Ao deletar convenio id: " + idFuncionario);
        }
    }

    @Transactional(readOnly = true)
    public Page<FuncionarioForListResponseDTO> findAllPaged(Pageable pageable) {
        String logCode = "findAllPaged(Pageable)";
        logger.info("{} - consulta paginada de recursos vide parametros {}", logCode, pageable);

        Page<Funcionario> list = funcionarioRepository.findAll(pageable);
        logger.info("{} - consulta paginada de recursos realizada com sucesso: {}", logCode, list);
        return list.map(itemFuncionarioEntity -> FuncionarioMapper.INSTANCE.deEntityParaRespresentacaoEmLista(itemFuncionarioEntity));
    }

    @Transactional(readOnly = true)
    public Page<FuncionarioForListResponseDTO> findAllPagedPorGrupoFuncao(GrupoFuncaoFuncionarioEnum grupoFuncao, Pageable pageable) {
        String logCode = "findAllPagedPorGrupoFuncao(GrupoFuncaoFuncionarioEnum,Pageable)";
        logger.info("{} - consulta paginada de recursos vide parametros {} e grupo funcao {}", logCode, pageable, grupoFuncao);

        var grupoFuncaoId = grupoFuncao.getCodigoGrupoFuncaoFuncionario();
        Page<Funcionario> list = funcionarioRepository.findFuncionarioByCodigoGrupoFuncao(grupoFuncaoId, pageable);
        logger.info("{} - consulta paginada de recursos por grupo de funcao realizada com sucesso: {}", logCode, list);
        return list.map(itemFuncionarioEntity -> FuncionarioMapper.INSTANCE.deEntityParaRespresentacaoEmLista(itemFuncionarioEntity));
    }

    @Transactional(readOnly = true)
    public FuncionarioDefaultResponseDTO findByIdFuncionario(String idFuncionario) {
        String logCode = "findByIdFuncionario(String)";
        logger.info("{} - buscando recurso pelo id: {}", logCode, idFuncionario);

        Optional<Funcionario> obj = funcionarioRepository.findFuncionarioByIdFuncionario(idFuncionario);
        Funcionario entity = obj.orElseThrow(() -> new ResourceNotFoundException("recurso nao encontrado id: " + idFuncionario));

        logger.info("{} - recurso encontrado: {}", logCode, entity);
        return FuncionarioMapper.INSTANCE.deFuncionarioParaDto(entity);
    }


    private Funcionario dtoToEntityOnCreate(FuncionarioInsertRequestDTO dto) {
        String logCode = "dtoToEntityOnCreate(FuncionarioInsertRequestDTO)";
        logger.info("{} - convertendo dto de cricao para entity {}", logCode, dto);

        var entity = FuncionarioMapper.INSTANCE.deDtoParaFuncionario(dto);

        entity.setIdFuncionario(UUID.randomUUID().toString());
        entity.setCodigoGrupoFuncao(dto.getGrupoFuncaoFuncionario().getCodigoGrupoFuncaoFuncionario());
        entity.setCodigoStatusFuncionario(StatusFuncionarioEnum.ATIVO.getCodigoStatusFuncionario());

        var senhaFuncionario = dto.getSenha();
        var senhaFuncionarioCriptografada = passwordEncoder.encode(senhaFuncionario);
        entity.setSenha(senhaFuncionarioCriptografada);

        entity.getRoles().clear();
        Role role;

        var grupoFuncaoFuncionario = dto.getGrupoFuncaoFuncionario();
        if (grupoFuncaoFuncionario.name().equalsIgnoreCase(GrupoFuncaoFuncionarioEnum.MEDICO.name())) {
            role = roleRepository.getOne(NivelPermissaoEnum.ROLE_MEDICO.getNivelPermissao());
        } else {
            role = roleRepository.getOne(NivelPermissaoEnum.ROLE_ADMINISTRATIVO.getNivelPermissao());
        }

        entity.getRoles().add(role);

        var endereco = entity.getEndereco();
        endereco.setCodigoEndereco(UUID.randomUUID().toString());
        entity.setEndereco(endereco);
        entity.getEspecialidades().clear();

        logger.info("{} - conversao realizada com sucesso {}", logCode, entity);
        return entity;
    }

    private FuncionarioDefaultResponseDTO entityToResponseDefault(Funcionario entity) {
        String logCode = "entityToResponseDefault(Funcionario)";
        logger.info("{} - convertendo entity para response default {}", logCode, entity);

        var responseDto = FuncionarioMapper.INSTANCE.deFuncionarioParaDto(entity);
        responseDto.setStatusFuncionario(StatusFuncionarioEnum.obterStatusFuncionario(entity.getCodigoStatusFuncionario()));
        responseDto.setGrupoFuncaoFuncionario(GrupoFuncaoFuncionarioEnum.obterGrupoFuncaoFuncionario(entity.getCodigoGrupoFuncao()));

        logger.info("{} - response default montado com sucesso {}", logCode, responseDto);
        return responseDto;
    }
}
