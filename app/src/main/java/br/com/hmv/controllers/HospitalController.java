package br.com.hmv.controllers;

import br.com.hmv.dtos.request.administrativo.HospitalAddEspecialidadeRequestDTO;
import br.com.hmv.dtos.request.administrativo.HospitalAtualizaStatusUnidadeRequestDTO;
import br.com.hmv.dtos.request.administrativo.HospitalRemoveEspecialidadeRequestDTO;
import br.com.hmv.dtos.request.administrativo.HospitalUnidadeInsertRequestDTO;
import br.com.hmv.dtos.responses.administrativo.HospitalDefaultResponseDTO;
import br.com.hmv.services.HospitalService;
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
@RequestMapping(value = "api/hospitais")
@AllArgsConstructor
public class HospitalController {
    private static Logger logger = LoggerFactory.getLogger(HospitalController.class);
    private HospitalService service;

    @PostMapping
    public ResponseEntity<HospitalDefaultResponseDTO> insert(@RequestBody @Valid HospitalUnidadeInsertRequestDTO requestDTO) {
        String logCode = "insert(HospitalUnidadeInsertRequestDTO)";
        logger.info("{} - solicitacao de inclusao {}", logCode, requestDTO);

        var responseDTO = service.criacao(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.getCodigoUnidade()).toUri();

        logger.info("{} - solicitacao de inclusao concluida com sucesso {}", logCode, responseDTO);
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<HospitalDefaultResponseDTO> updateStatus(@PathVariable String id, @RequestBody @Valid HospitalAtualizaStatusUnidadeRequestDTO requestDTO) {
        String logCode = "updateStatus(String, HospitalAtualizaStatusUnidadeRequestDTO)";
        logger.info("{} - solicitacao de atualizacao de status {}", logCode, requestDTO);

        HospitalDefaultResponseDTO responseDTO = service.updateStatus(id, requestDTO);

        logger.info("{} - solicitacao de atualizacao concluida com sucesso {}", logCode, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<HospitalDefaultResponseDTO>> findAll(Pageable pageable) {
        String logCode = "findAll(Pageable)";
        logger.info("{} - solicitacao de consulta todos paginada {}", logCode, pageable);

        Page<HospitalDefaultResponseDTO> responseDtoInList = service.findAllPaged(pageable);

        logger.info("{} - solicitacao de consulta todos paginada realizada com sucesso{}", logCode, pageable);
        return ResponseEntity.ok().body(responseDtoInList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HospitalDefaultResponseDTO> findById(@PathVariable String id) {
        String logCode = "findById(String)";
        logger.info("{} - solicitacao de consulta detalhe {}", logCode, id);

        HospitalDefaultResponseDTO responseDTO = service.findByIdCodigoUnidade(id);

        logger.info("{} - solicitacao de consulta detalhe realizada com sucesso {}", logCode, responseDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        String logCode = "delete(String)";
        logger.info("{} - solicitacao de delete {}", logCode, id);

        service.delete(id);

        logger.info("{} - solicitacao de delete realizada com sucesso {}", logCode, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/especialidades")
    public ResponseEntity<HospitalDefaultResponseDTO> addEspecialidade(@PathVariable String id, @RequestBody @Valid HospitalAddEspecialidadeRequestDTO requestDTO) {
        String logCode = "addEspecialidade(String, HospitalAddEspecialidadeRequestDTO)";
        logger.info("{} - solicitacao de adicao de especialidade {}", logCode, requestDTO);

        HospitalDefaultResponseDTO responseDTO = service.addEspecialidade(id, requestDTO);

        logger.info("{} - solicitacao de atualizacao concluida com sucesso {}", logCode, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping(value = "/{id}/especialidades")
    public ResponseEntity<HospitalDefaultResponseDTO> removeEspecialidade(@PathVariable String id, @RequestBody @Valid HospitalRemoveEspecialidadeRequestDTO requestDTO) {
        String logCode = "removeEspecialidade(String, HospitalRemoveEspecialidadeRequestDTO)";
        logger.info("{} - solicitacao de remocao de especialidade {}", logCode, requestDTO);

        HospitalDefaultResponseDTO responseDTO = service.removeEspecialidade(id, requestDTO);

        logger.info("{} - solicitacao de remocao de especialidade concluida com sucesso {}", logCode, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }
}
