package br.com.hmv.services;

import br.com.hmv.dtos.request.administrativo.EspecialidadeAtualizaStatusRequestDTO;
import br.com.hmv.dtos.request.administrativo.EspecialidadeInsertRequestDTO;
import br.com.hmv.dtos.responses.administrativo.EspecialidadeDefaultResponseDTO;
import br.com.hmv.exceptions.DatabaseException;
import br.com.hmv.exceptions.ResourceNotFoundException;
import br.com.hmv.models.entities.Especialidade;
import br.com.hmv.models.enums.StatusEspecialidadeEnum;
import br.com.hmv.repositories.EspecialidadeRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EspecialidadeService {
    private static Logger logger = LoggerFactory.getLogger(EspecialidadeService.class);
    private EspecialidadeRepository repository;

    @Transactional
    public EspecialidadeDefaultResponseDTO criacao(EspecialidadeInsertRequestDTO dto) {
        String logCode = "criacao(EspecialidadeInsertRequestDTO)";
        logger.info("{} - solicitacao de inclusao {}", logCode, dto);

        Especialidade entity = new Especialidade();
        dtoToEntityOnCreate(dto, entity);
        entity = repository.save(entity);

        logger.info("{} - Especialidade incluida com sucesso {}", logCode, entity);
        return new EspecialidadeDefaultResponseDTO(entity);
    }

    @Transactional
    public EspecialidadeDefaultResponseDTO updateStatus(Long id, EspecialidadeAtualizaStatusRequestDTO dto) {
        String logCode = "updateStatus(long)";
        logger.info("{} - solicitacao de atualizacao de status {}", logCode, dto);

        try {
            Especialidade entity = repository.getOne(id);

            //passa status novo
            entity.setCodigoStatusEspecialidade(dto.getStatusEspecialidade().getCodigoStatusEspecialidade());
            entity = repository.save(entity);

            logger.info("{} - atualizacao realizada com sucesso {}", logCode, entity);
            return new EspecialidadeDefaultResponseDTO(entity);
        } catch (EntityNotFoundException e) {
            logger.warn("{} - recurso nao encontrado id: {} ", id);
            throw new ResourceNotFoundException("Especialidade nao encontrada id: " + id);
        }
    }


    @Transactional
    public void delete(Long id) {
        String logCode = "delete(Long)";
        logger.info("{} - deletando recurso: {}", logCode, id);

        try {
            repository.deleteById(id);
            logger.info("{} - recurso deletado com sucesso: {}", logCode, id);

        } catch (EmptyResultDataAccessException e) {
            logger.warn("{} - recurso nao encontrado: {}", logCode, id);
            throw new ResourceNotFoundException("Convenio nao encontrado id: " + id);
        } catch (DataIntegrityViolationException e) {
            logger.warn("{} - erro de integridade de dados: {}", logCode, id);
            throw new DatabaseException("Integrity violation - Ao deletar convenio id: " + id);
        }
    }

    @Transactional(readOnly = true)
    public Page<EspecialidadeDefaultResponseDTO> findAllPaged(Pageable pageable) {
        String logCode = "findAllPaged(Pageable)";
        logger.info("{} - consulta paginada de recursos vide parametros {}", logCode, pageable);

        Page<Especialidade> list = repository.findAll(pageable);
        logger.info("{} - consulta paginada de recursos realizada com sucesso: {}", logCode, list);
        return list.map(itemConvenioEntity -> new EspecialidadeDefaultResponseDTO(itemConvenioEntity));
    }

    @Transactional(readOnly = true)
    public EspecialidadeDefaultResponseDTO findById(Long id) {
        String logCode = "findById(Long)";
        logger.info("{} - buscando recurso pelo id: {}", logCode, id);

        Optional<Especialidade> obj = repository.findById(id);
        Especialidade entity = obj.orElseThrow(() -> new ResourceNotFoundException("Recurso nao encontrado id: " + id));

        logger.info("{} - recurso encontrado: {}", logCode, entity);
        return new EspecialidadeDefaultResponseDTO(entity);
    }

    private void dtoToEntityOnCreate(EspecialidadeInsertRequestDTO dto, Especialidade entity) {
        String logCode = "dtoToEntityOnCreate(EspecialidadeDTO, Especialidade)";
        logger.info("{} - convertendo dto de cricao para entity {}", logCode, dto);

        entity.setNome(dto.getNome());
        entity.setCodigoStatusEspecialidade(StatusEspecialidadeEnum.ATIVA.getCodigoStatusEspecialidade());
        logger.info("{} - conversao realizada com sucesso {}", logCode, entity);
    }
}
