package br.com.hmv.controllers;

import br.com.hmv.dtos.request.administrativo.ConvenioAtualizaAllRequestDTO;
import br.com.hmv.dtos.request.administrativo.ConvenioAtualizaStatusRequestDTO;
import br.com.hmv.dtos.request.administrativo.ConvenioInsertRequestDTO;
import br.com.hmv.dtos.responses.administrativo.ConvenioDefaultResponseDTO;
import br.com.hmv.services.ConvenioService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "api/convenios")
@AllArgsConstructor
public class ConvenioController {
    private static Logger logger = LoggerFactory.getLogger(ConvenioController.class);
    private ConvenioService service;

    @PostMapping
    public ResponseEntity<ConvenioDefaultResponseDTO> insert(@RequestBody @Valid ConvenioInsertRequestDTO requestDTO) {
        String logCode = "insert(ConvenioInsertRequestDTO)";
        logger.info("{} - solicitacao de inclusao {}", logCode, requestDTO);

        var responseDTO = service.criacao(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.getId()).toUri();

        logger.info("{} - solicitacao de inclusao concluida com sucesso {}", logCode, responseDTO);
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<ConvenioDefaultResponseDTO> updateStatus(@PathVariable Long id, @RequestBody @Valid ConvenioAtualizaStatusRequestDTO requestDTO) {
        String logCode = "updateStatus(Long, ConvenioAtualizaStatusRequestDTO)";
        logger.info("{} - solicitacao de atualizacao de status {}", logCode, requestDTO);

        ConvenioDefaultResponseDTO responseDTO = service.updateStatus(id, requestDTO);

        logger.info("{} - solicitacao de atualizacao concluida com sucesso {}", logCode, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ConvenioDefaultResponseDTO> updateAll(@PathVariable Long id, @RequestBody @Valid ConvenioAtualizaAllRequestDTO requestDTO) {
        String logCode = "updateAll(Long, ConvenioAtualizaAllRequestDTO)";
        logger.info("{} - solicitacao de atualizacao total {}", logCode, requestDTO);

        ConvenioDefaultResponseDTO responseDTO = service.updateAll(id, requestDTO);

        logger.info("{} - solicitacao de atualizacao total concluida com sucesso {}", logCode, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ConvenioDefaultResponseDTO>> findAll(Pageable pageable) {
        String logCode = "findAll(Pageable)";
        logger.info("{} - solicitacao de consulta todos paginada {}", logCode, pageable);

        Page<ConvenioDefaultResponseDTO> responseDtoInList = service.findAllPaged(pageable);

        logger.info("{} - solicitacao de consulta todos paginada realizada com sucesso{}", logCode, pageable);
        return ResponseEntity.ok().body(responseDtoInList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConvenioDefaultResponseDTO> findById(@PathVariable Long id) {
        String logCode = "findById(Long)";
        logger.info("{} - solicitacao de consulta detalhe {}", logCode, id);

        ConvenioDefaultResponseDTO responseDTO = service.findById(id);

        logger.info("{} - solicitacao de consulta detalhe realizada com sucesso {}", logCode, responseDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String logCode = "delete(Long)";
        logger.info("{} - solicitacao de delete {}", logCode, id);

        service.delete(id);

        logger.info("{} - solicitacao de delete realizada com sucesso {}", logCode, id);
        return ResponseEntity.noContent().build();
    }
}
