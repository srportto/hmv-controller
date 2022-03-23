package br.com.hmv.controllers;

import br.com.hmv.dtos.request.administrativo.EspecialidadeAtualizaStatusRequestDTO;
import br.com.hmv.dtos.request.administrativo.EspecialidadeInsertRequestDTO;
import br.com.hmv.dtos.responses.administrativo.EspecialidadeDefaultResponseDTO;
import br.com.hmv.services.EspecialidadeService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "api/especialidades")
@AllArgsConstructor
public class EspecialidadeController {
    private static Logger logger = LoggerFactory.getLogger(EspecialidadeController.class);
    private EspecialidadeService service;

    @PostMapping
    public ResponseEntity<EspecialidadeDefaultResponseDTO> insert(@RequestBody @Valid EspecialidadeInsertRequestDTO requestDTO) {
        String logCode = "insert(EspecialidadeInsertRequestDTO)";
        logger.info("{} - solicitacao de inclusao {}", logCode, requestDTO);

        var responseDTO = service.criacao(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.getId()).toUri();

        logger.info("{} - solicitacao de inclusao concluida com sucesso {}", logCode, responseDTO);
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<EspecialidadeDefaultResponseDTO> updateStatus(@PathVariable Long id, @RequestBody @Valid EspecialidadeAtualizaStatusRequestDTO requestDTO) {
        String logCode = "updateStatus(String, EspecialidadeAtualizaStatusRequestDTO)";
        logger.info("{} - solicitacao de atualizacao de status {}", logCode, requestDTO);

        EspecialidadeDefaultResponseDTO responseDTO = service.updateStatus(id, requestDTO);

        logger.info("{} - solicitacao de atualizacao concluida com sucesso {}", logCode, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<EspecialidadeDefaultResponseDTO>> findAll(Pageable pageable) {
        String logCode = "findAll(Pageable)";
        logger.info("{} - solicitacao de consulta todos paginada {}", logCode, pageable);

        Page<EspecialidadeDefaultResponseDTO> responseDtoInList = service.findAllPaged(pageable);

        logger.info("{} - solicitacao de consulta todos paginada realizada com sucesso {}", logCode, pageable);
        return ResponseEntity.ok().body(responseDtoInList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EspecialidadeDefaultResponseDTO> findById(@PathVariable Long id) {
        String logCode = "findById(Long)";
        logger.info("{} - solicitacao de consulta detalhe {}", logCode, id);

        EspecialidadeDefaultResponseDTO responseDTO = service.findById(id);

        logger.info("{} - solicitacao de consulta detalhe realizada com sucesso {}", logCode, responseDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String logCode = "delete(String)";
        logger.info("{} - solicitacao de delete {}", logCode, id);

        service.delete(id);

        logger.info("{} - solicitacao de delete realizada com sucesso {}", logCode, id);
        return ResponseEntity.noContent().build();
    }
}
