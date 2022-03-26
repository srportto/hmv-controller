package br.com.hmv.controllers;


import br.com.hmv.dtos.request.paciente.PacienteInsertRequestDTO;
import br.com.hmv.dtos.request.paciente.PacienteUpdateAllRequestDTO;
import br.com.hmv.dtos.responses.paciente.PacienteDefaultResponseDTO;
import br.com.hmv.dtos.responses.paciente.PacienteForListResponseDTO;
import br.com.hmv.dtos.responses.paciente.PacienteInsertResponseDTO;
import br.com.hmv.services.PacienteService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "api/pacientes")
@AllArgsConstructor
public class PacienteController {
    private static Logger logger = LoggerFactory.getLogger(PacienteController.class);
    private PacienteService service;

    @PostMapping
    public ResponseEntity<PacienteInsertResponseDTO> insert(@RequestBody @Valid PacienteInsertRequestDTO requestDTO) {
        String logCode = "insert(PacienteInsertRequestDTO)";
        logger.info("{} - solicitacao de inclusao {}", logCode, requestDTO);

        var responseDTO = service.criacao(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.getIdPaciente()).toUri();

        logger.info("{} - solicitacao de inclusao concluida com sucesso {}", logCode, responseDTO);
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<PacienteDefaultResponseDTO> updateAll(@PathVariable String id, @RequestBody @Valid PacienteUpdateAllRequestDTO requestDTO) {
        String logCode = "updateAll(String, PacienteUpdateAllRequestDTO)";
        logger.info("{} - solicitacao de atualizacao de dados {}", logCode, requestDTO);

        PacienteDefaultResponseDTO responseDTO = service.updateAll(id, requestDTO);

        logger.info("{} - solicitacao de atualizacao concluida com sucesso {}", logCode, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PacienteDefaultResponseDTO> findById(@PathVariable String id) {
        String logCode = "findById(String)";
        logger.info("{} - solicitacao de consulta detalhe {}", logCode, id);

        PacienteDefaultResponseDTO responseDTO = service.findByIdPaciente(id);

        logger.info("{} - solicitacao de consulta detalhe realizada com sucesso {}", logCode, responseDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PacienteForListResponseDTO>> findAll(Pageable pageable) {
        String logCode = "findAll(Pageable)";
        logger.info("{} - solicitacao de consulta todos paginada {}", logCode, pageable);

        Page<PacienteForListResponseDTO> responseDtoInList = service.findAllPaged(pageable);

        logger.info("{} - solicitacao de consulta todos paginada realizada com sucesso{}", logCode, pageable);
        return ResponseEntity.ok().body(responseDtoInList);
    }

}
