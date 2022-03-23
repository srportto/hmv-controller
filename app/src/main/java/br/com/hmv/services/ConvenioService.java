package br.com.hmv.services;

import br.com.hmv.dtos.request.administrativo.ConvenioAtualizaAllRequestDTO;
import br.com.hmv.dtos.request.administrativo.ConvenioAtualizaStatusRequestDTO;
import br.com.hmv.dtos.request.administrativo.ConvenioInsertRequestDTO;
import br.com.hmv.dtos.responses.administrativo.ConvenioDefaultResponseDTO;
import br.com.hmv.exceptions.DatabaseException;
import br.com.hmv.exceptions.ResourceNotFoundException;
import br.com.hmv.models.entities.Convenio;
import br.com.hmv.models.enums.StatusConvenioEnum;
import br.com.hmv.repositories.ConvenioRepository;
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
public class ConvenioService {
    private static Logger logger = LoggerFactory.getLogger(ConvenioService.class);
    private ConvenioRepository repository;

    @Transactional
    public ConvenioDefaultResponseDTO criacao(ConvenioInsertRequestDTO dto) {
        String logCode = "criacao(ConvenioInsertRequestDTO)";
        logger.info("{} - solicitacao de inclusao {}", logCode, dto);

        Convenio entity = new Convenio();
        dtoToEntityOnCreate(dto, entity);
        entity = repository.save(entity);

        logger.info("{} - Convenio incluido com sucesso {}", logCode, entity);
        return new ConvenioDefaultResponseDTO(entity);
    }

    @Transactional
    public ConvenioDefaultResponseDTO updateStatus(Long id, ConvenioAtualizaStatusRequestDTO dto) {
        String logCode = "updateStatus(long)";
        logger.info("{} - solicitacao de atualizacao de status {}", logCode, dto);

        try {
            Convenio entity = repository.getOne(id);

            //passa status novo
            entity.setCodigoStatusConvenio(dto.getStatusConvenioEnum().getCodigoStatusConvenio());
            entity = repository.save(entity);

            logger.info("{} - atualizacao realizada com sucesso {}", logCode, entity);
            return new ConvenioDefaultResponseDTO(entity);
        } catch (EntityNotFoundException e) {
            logger.warn("{} - recurso nao encontrado id: {} ", logCode, id);
            throw new ResourceNotFoundException("Convenio nao encontrado id: " + id);
        }
    }

    @Transactional
    public ConvenioDefaultResponseDTO updateAll(Long id, ConvenioAtualizaAllRequestDTO dto) {
        String logCode = "updateAll(Long,ConvenioAtualizaAllRequestDTO)";
        logger.info("{} - atualizando recurso: {}", logCode, dto);

        try {
            Convenio entity = repository.getOne(id);
            //Passa dados atualizados
            entity.setCodigoStatusConvenio(dto.getStatusConvenioEnum().getCodigoStatusConvenio());
            entity.setDescricao(dto.getDescricao());
            entity = repository.save(entity);

            logger.info("{} - recurso atualizado id: {}", logCode, entity);
            return new ConvenioDefaultResponseDTO(entity);

        } catch (EntityNotFoundException e) {
            logger.warn("{} - recurso nao encontrado id: {}", logCode, id);
            throw new ResourceNotFoundException("Convenio nao encontrado id: " + id);
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
    public Page<ConvenioDefaultResponseDTO> findAllPaged(Pageable pageable) {
        String logCode = "findAllPaged(Pageable)";
        logger.info("{} - consulta paginada de recursos vide parametros {}", logCode, pageable);

        Page<Convenio> list = repository.findAll(pageable);
        logger.info("{} - consulta paginada de recursos realizada com sucesso: {}", logCode, list);
        return list.map(itemConvenioEntity -> new ConvenioDefaultResponseDTO(itemConvenioEntity));
    }

    @Transactional(readOnly = true)
    public ConvenioDefaultResponseDTO findById(Long id) {
        String logCode = "findById(Long)";
        logger.info("{} - buscando recurso pelo id: {}", logCode, id);

        Optional<Convenio> obj = repository.findById(id);
        Convenio entity = obj.orElseThrow(() -> new ResourceNotFoundException("Recurso nao encontrado id: " + id));

        logger.info("{} - recurso encontrado: {}", logCode, entity);
        return new ConvenioDefaultResponseDTO(entity);
    }

    private void dtoToEntityOnCreate(ConvenioInsertRequestDTO dto, Convenio entity) {
        String logCode = "dtoToEntityOnCreate(ConvenioInsertRequestDTO, Convenio)";
        logger.info("{} - convertendo dto de cricao para entity {}", logCode, dto);

        entity.setDescricao(dto.getDescricao());
        entity.setCodigoStatusConvenio(StatusConvenioEnum.ATIVO.getCodigoStatusConvenio());
        logger.info("{} - conversao realizada com sucesso {}", logCode, entity);
    }
}
