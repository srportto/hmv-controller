package br.com.hmv.controllers;

import br.com.hmv.dtos.request.administrativo.FuncionarioAddEspecialidadeRequestDTO;
import br.com.hmv.dtos.request.administrativo.FuncionarioAtualizaStatusRequestDTO;
import br.com.hmv.dtos.request.administrativo.FuncionarioInsertRequestDTO;
import br.com.hmv.dtos.request.administrativo.FuncionarioRemoveEspecialidadeRequestDTO;
import br.com.hmv.dtos.responses.administrativo.FuncionarioDefaultResponseDTO;
import br.com.hmv.dtos.responses.administrativo.FuncionarioForListResponseDTO;
import br.com.hmv.models.enums.GrupoFuncaoFuncionarioEnum;
import br.com.hmv.services.FuncionarioService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "api/funcionarios")
@AllArgsConstructor
public class FuncionarioController {
    private static Logger logger = LoggerFactory.getLogger(FuncionarioController.class);
    private FuncionarioService service;

    @PostMapping
    public ResponseEntity<FuncionarioDefaultResponseDTO> insert(@RequestBody @Valid FuncionarioInsertRequestDTO requestDTO) {
        String logCode = "insert(FuncionarioInsertRequestDTO)";
        logger.info("{} - solicitacao de inclusao {}", logCode, requestDTO);

        var responseDTO = service.criacao(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.getIdFuncionario()).toUri();

        logger.info("{} - solicitacao de inclusao concluida com sucesso {}", logCode, responseDTO);
        return ResponseEntity.created(uri).body(responseDTO);
    }


    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<FuncionarioDefaultResponseDTO> updateStatus(@PathVariable String id, @RequestBody @Valid FuncionarioAtualizaStatusRequestDTO requestDTO) {
        String logCode = "updateStatus(String, FuncionarioAtualizaStatusRequestDTO)";
        logger.info("{} - solicitacao de atualizacao de status {}", logCode, requestDTO);

        FuncionarioDefaultResponseDTO responseDTO = service.updateStatus(id, requestDTO);

        logger.info("{} - solicitacao de atualizacao concluida com sucesso {}", logCode, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<FuncionarioForListResponseDTO>> findAll(Pageable pageable) {
        String logCode = "findAll(Pageable)";
        logger.info("{} - solicitacao de consulta todos paginada {}", logCode, pageable);

        Page<FuncionarioForListResponseDTO> responseDtoInList = service.findAllPaged(pageable);

        logger.info("{} - solicitacao de consulta todos paginada realizada com sucesso{}", logCode, pageable);
        return ResponseEntity.ok().body(responseDtoInList);
    }

    @GetMapping(value = "/grupo-funcao")
    public ResponseEntity<Page<FuncionarioForListResponseDTO>> findAllByGrupoFuncao(@RequestParam GrupoFuncaoFuncionarioEnum grupo, Pageable pageable) {
        String logCode = "findAllByGrupoFuncao(GrupoFuncaoFuncionarioEnum,Pageable)";
        logger.info("{} - solicitacao de consulta todos paginada {} por grupo de funcao {}", logCode, pageable, grupo);

        Page<FuncionarioForListResponseDTO> responseDtoInList = service.findAllPagedPorGrupoFuncao(grupo, pageable);

        logger.info("{} - solicitacao de consulta todos paginada realizada com sucesso{}", logCode, pageable);
        return ResponseEntity.ok().body(responseDtoInList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FuncionarioDefaultResponseDTO> findById(@PathVariable String id) {
        String logCode = "findById(String)";
        logger.info("{} - solicitacao de consulta detalhe {}", logCode, id);

        FuncionarioDefaultResponseDTO responseDTO = service.findByIdFuncionario(id);

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
    public ResponseEntity<FuncionarioDefaultResponseDTO> addEspecialidade(@PathVariable String id, @RequestBody @Valid FuncionarioAddEspecialidadeRequestDTO requestDTO) {
        String logCode = "addEspecialidade(String, FuncionarioAddEspecialidadeRequestDTO)";
        logger.info("{} - solicitacao de adicao de especialidade {}", logCode, requestDTO);

        FuncionarioDefaultResponseDTO responseDTO = service.addEspecialidade(id, requestDTO);

        logger.info("{} - solicitacao de adicao de especialidade concluida com sucesso {}", logCode, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping(value = "/{id}/especialidades")
    public ResponseEntity<FuncionarioDefaultResponseDTO> removeEspecialidade(@PathVariable String id, @RequestBody @Valid FuncionarioRemoveEspecialidadeRequestDTO requestDTO) {
        String logCode = "removeEspecialidade(String, FuncionarioRemoveEspecialidadeRequestDTO)";
        logger.info("{} - solicitacao de remocao de especialidade {}", logCode, requestDTO);

        FuncionarioDefaultResponseDTO responseDTO = service.removeEspecialidade(id, requestDTO);

        logger.info("{} - solicitacao de remocao de especialidade concluida com sucesso {}", logCode, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }
}
