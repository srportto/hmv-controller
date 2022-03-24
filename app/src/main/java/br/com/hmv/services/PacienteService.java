package br.com.hmv.services;

import br.com.hmv.dtos.request.paciente.ConvenioRequestDTO;
import br.com.hmv.dtos.request.paciente.EnderecoRequestDTO;
import br.com.hmv.dtos.request.paciente.PacienteInsertRequestDTO;
import br.com.hmv.dtos.request.paciente.PacienteUpdateAllRequestDTO;
import br.com.hmv.dtos.request.paciente.TelefoneRequestDTO;
import br.com.hmv.dtos.responses.paciente.PacienteDefaultResponseDTO;
import br.com.hmv.dtos.responses.paciente.PacienteInsertResponseDTO;
import br.com.hmv.exceptions.ResourceNotFoundException;
import br.com.hmv.models.entities.ConvenioPaciente;
import br.com.hmv.models.entities.EnderecoPaciente;
import br.com.hmv.models.entities.Paciente;
import br.com.hmv.models.entities.Role;
import br.com.hmv.models.entities.Telefone;
import br.com.hmv.models.enums.CadastroPacienteEnum;
import br.com.hmv.models.enums.NivelPermissaoEnum;
import br.com.hmv.models.mappers.PacienteMapper;
import br.com.hmv.repositories.PacienteRepository;
import br.com.hmv.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PacienteService {
    private static Logger logger = LoggerFactory.getLogger(PacienteService.class);
    private PacienteRepository pacienteRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public PacienteInsertResponseDTO criacao(PacienteInsertRequestDTO dto) {
        String logCode = "criacao(PacienteInsertRequestDTO)";
        logger.info("{} - solicitacao de inclusao {}", logCode, dto);

        var entity = dtoToEntityOnCreate(dto);
        entity = pacienteRepository.save(entity);

        logger.info("{} - Convenio incluido com sucesso {}", logCode, entity);
        return entityToResponseDtoInsert(entity);
    }

    @Transactional
    public PacienteDefaultResponseDTO updateAll(String idPaciente, PacienteUpdateAllRequestDTO dto) {
        String logCode = "updateStatus(String, PacienteUpdateAllRequestDTO)";
        logger.info("{} - solicitacao de atualizacao de dados do paciente {}", logCode, dto);

        try {
            var objOptional = pacienteRepository.findPacienteByIdPaciente(idPaciente);
            Paciente entity = objOptional.orElseThrow(() -> new ResourceNotFoundException("recurso nao encontrado id: " + idPaciente));

            preparaParaAtualizarAll(entity, dto);
            var entityAtualizada = pacienteRepository.save(entity);

            logger.info("{} - atualizacao realizada com sucesso {}", logCode, entityAtualizada);
            return PacienteMapper.INSTANCE.deEntityParaDtoDefault(entityAtualizada);

        } catch (EntityNotFoundException e) {
            logger.warn("{} - recurso nao encontrado id: {} ", idPaciente);
            throw new ResourceNotFoundException("Recurso nao encontrado id: " + idPaciente);
        }
    }


    @Transactional(readOnly = true)
    public PacienteDefaultResponseDTO findByIdPaciente(String idPaciente) {
        String logCode = "findByIdPaciente(String)";
        logger.info("{} - buscando recurso pelo id: {}", logCode, idPaciente);

        Optional<Paciente> obj = pacienteRepository.findPacienteByIdPaciente(idPaciente);
        Paciente entity = obj.orElseThrow(() -> new ResourceNotFoundException("recurso nao encontrado id: " + idPaciente));

        logger.info("{} - recurso encontrado: {}", logCode, entity);
        return PacienteMapper.INSTANCE.deEntityParaDtoDefault(entity);
    }


    private Paciente dtoToEntityOnCreate(PacienteInsertRequestDTO dto) {
        String logCode = "dtoToEntityOnCreate(PacienteInsertRequestDTO)";
        logger.info("{} - convertendo dto de cricao para entity {}", logCode, dto);

        var entity = PacienteMapper.INSTANCE.deDtoParaEntity(dto);

        entity.setIdPaciente(UUID.randomUUID().toString());
        entity.setIndicadorTipoCadastroRealizado(CadastroPacienteEnum.SIMPLES.getCodigoStatusCadastroPaciente());

        var senhaPaciente = dto.getSenha();
        var senhaPacienteCriptografada = passwordEncoder.encode(senhaPaciente);
        entity.setSenha(senhaPacienteCriptografada);

        // setando a Role default do paciente
        entity.getRoles().clear();
        Role role = roleRepository.getOne(NivelPermissaoEnum.ROLE_PACIENTE.getNivelPermissao());
        entity.getRoles().add(role);

        EnderecoPaciente endereco = new EnderecoPaciente();
        endereco.setCodigoEndereco(UUID.randomUUID().toString());
        entity.setEndereco(endereco);

        logger.info("{} - conversao realizada com sucesso {}", logCode, entity);
        return entity;
    }

    private PacienteInsertResponseDTO entityToResponseDtoInsert(Paciente entity) {
        String logCode = "entityToResponseDefault(Paciente)";
        logger.info("{} - convertendo entity para response default {}", logCode, entity);

        var responseDto = PacienteMapper.INSTANCE.deEntityParaDtoInsert(entity);
        responseDto.setIndicadorCadastro(CadastroPacienteEnum.obterStatusCadastroPaciente(entity.getIndicadorTipoCadastroRealizado()));
        logger.info("{} - response default montado com sucesso {}", logCode, responseDto);
        return responseDto;
    }

    private void preparaParaAtualizarAll(Paciente entity, PacienteUpdateAllRequestDTO dto) {
        String logCode = "preparaParaAtualizarAll(Paciente)";
        logger.info("{} - atualizacao de dados do paciente - de atual {} para novos {}", logCode, entity, dto);

        var tipoCadastroRealizado = CadastroPacienteEnum.obterStatusCadastroPaciente(entity.getIndicadorTipoCadastroRealizado());
        if (tipoCadastroRealizado.name().equalsIgnoreCase(CadastroPacienteEnum.SIMPLES.name())) {
            Telefone telefone = new Telefone();
            ConvenioPaciente convenio = new ConvenioPaciente();

            entity.setTelefone(telefone);
            entity.setConvenio(convenio);
            entity.setIndicadorTipoCadastroRealizado(CadastroPacienteEnum.COMPLETO.getCodigoStatusCadastroPaciente());
        }

        //para atualizar nomeCompleto
        if (dto.getNomeCompleto() != null && !dto.getNomeCompleto().isEmpty()) {
            entity.setNomeCompleto(dto.getNomeCompleto());
        }

        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            entity.setEmail(dto.getEmail());
        }

        //para atualizar nomeCompleto da mae
        if (dto.getNomeCompletoMae() != null && !dto.getNomeCompletoMae().isEmpty()) {
            entity.setNomeCompletoMae(dto.getNomeCompletoMae());
        }

        //para atualizar nomeCompleto do pai
        if (dto.getNomeCompletoPai() != null && !dto.getNomeCompletoPai().isEmpty()) {
            entity.setNomeCompletoPai(dto.getNomeCompletoPai());
        }

        //para atualizar endereco
        if (dto.getEndereco() != null) {
            var enderecoDto = dto.getEndereco();
            var enderecoEntity = entity.getEndereco();
            atualizarEndereco(enderecoEntity, enderecoDto);
        }

        //para atualizar telefone
        if (dto.getTelefone() != null) {
            var telefoneDto = dto.getTelefone();
            var telefoneEntity = entity.getTelefone();
            atualizarTelefone(telefoneEntity, telefoneDto);
        }

        //para atualizar convenio
        if (dto.getConvenio() != null) {
            var convenioDto = dto.getConvenio();
            var convenioEntity = entity.getConvenio();
            atualizarConvenio(convenioEntity, convenioDto);
        }

        logger.info("{} - dados do paciente atualizados {}", logCode, entity);
    }

    private void atualizarEndereco(EnderecoPaciente enderecoEntity, EnderecoRequestDTO enderecoDto) {
        String logCode = "atualizarEndereco(Endereco, EnderecoRequestDTO )";
        logger.info("{} - atualizacao de endereco do paciente - de atual {} para novos {}", logCode, enderecoEntity, enderecoDto);

        if (enderecoDto.getDescricao() != null && !enderecoDto.getDescricao().isEmpty()) {
            enderecoEntity.setDescricao(enderecoDto.getDescricao());
        }

        if (enderecoDto.getLogradouro() != null && !enderecoDto.getLogradouro().isEmpty()) {
            enderecoEntity.setLogradouro(enderecoDto.getLogradouro());
        }

        if (enderecoDto.getNumero() != null) {
            enderecoEntity.setNumero(enderecoDto.getNumero());
        }

        if (enderecoDto.getComplemento() != null && !enderecoDto.getComplemento().isEmpty()) {
            enderecoEntity.setComplemento(enderecoDto.getComplemento());
        }

        if (enderecoDto.getCidade() != null && !enderecoDto.getCidade().isEmpty()) {
            enderecoEntity.setCidade(enderecoDto.getCidade());
        }

        if (enderecoDto.getUf() != null && !enderecoDto.getUf().isEmpty()) {
            enderecoEntity.setUf(enderecoDto.getUf());
        }

        if (enderecoDto.getCep() != null) {
            enderecoEntity.setCep(enderecoDto.getCep());
        }

        logger.info("{} - dados do endereco paciente atualizados {}", logCode, enderecoEntity);
    }

    private void atualizarTelefone(Telefone telefoneEntity, TelefoneRequestDTO telefoneDTO) {
        String logCode = "atualizarTelefone(Telefone, TelefoneRequestDTO)";
        logger.info("{} - atualizacao de telefone do paciente - de atual {} para novos {}", logCode, telefoneEntity, telefoneDTO);

        if (telefoneDTO.getCodigoPais() != null) {
            telefoneEntity.setCodigoPais(telefoneDTO.getCodigoPais());
        }

        if (telefoneDTO.getCodigoArea() != null) {
            telefoneEntity.setCodigoArea(telefoneDTO.getCodigoArea());
        }

        if (telefoneDTO.getNumero() != null) {
            telefoneEntity.setNumero(telefoneDTO.getNumero());
        }

        if (telefoneDTO.getDescricao() != null && !telefoneDTO.getDescricao().isEmpty()) {
            telefoneEntity.setDescricao(telefoneDTO.getDescricao());
        }

        logger.info("{} - dados do telefone do paciente atualizados {}", logCode, telefoneEntity);
    }

    private void atualizarConvenio(ConvenioPaciente entity, ConvenioRequestDTO dto) {
        String logCode = "atualizarConvenio(Convenio, ConvenioRequestDTO)";
        logger.info("{} - atualizacao de convenio do paciente - de atual {} para novos {}", logCode, entity, dto);

        if (dto.getCodigoConvenio() != null && !dto.getCodigoConvenio().isEmpty()) {
            entity.setCodigoConvenio(dto.getCodigoConvenio());
        }

        if (dto.getDescricaoConvenio() != null && !dto.getDescricaoConvenio().isEmpty()) {
            entity.setDescricaoConvenio(dto.getDescricaoConvenio());
        }

        if (dto.getNumeroCarteiraConvenio() != null && !dto.getNumeroCarteiraConvenio().isEmpty()) {
            entity.setNumeroCarteiraConvenio(dto.getNumeroCarteiraConvenio());
        }

        if (dto.getDataValidadeConvenio() != null) {
            entity.setDataValidadeConvenio(dto.getDataValidadeConvenio());
        }

        logger.info("{} - dados do convenio do paciente atualizados {}", logCode, entity);
    }
}
