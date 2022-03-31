package br.com.hmv.services;

import br.com.hmv.configurations.components.UserMaster;
import br.com.hmv.models.entities.EnderecoAdministrativo;
import br.com.hmv.models.entities.Funcionario;
import br.com.hmv.models.entities.Paciente;
import br.com.hmv.models.entities.Role;
import br.com.hmv.models.entities.Telefone;
import br.com.hmv.models.enums.GeneroPessoasEnum;
import br.com.hmv.models.enums.GrupoFuncaoFuncionarioEnum;
import br.com.hmv.models.enums.NivelPermissaoEnum;
import br.com.hmv.models.enums.StatusFuncionarioEnum;
import br.com.hmv.repositories.FuncionarioRepository;
import br.com.hmv.repositories.PacienteRepository;
import br.com.hmv.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {
    private static Logger logger = LoggerFactory.getLogger(AuthService.class);
    private PacienteRepository pacienteRepository;
    private FuncionarioRepository funcionarioRepository;
    private RoleRepository roleRepository;
    private UserMaster userMaster;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String logCode = "loadUserByUsername(String)";
        logger.info("{} - solicitacao de consulta usuario(paciente/funcionario) para autenticacao:  {}", logCode, username);

        UserDetails user = null;

        Paciente paciente = pacienteRepository.findPacienteByEmail(username);
        if (paciente != null) {
            user = paciente;
            logger.info("{} - usuario encontrado eh um paciente {}  ", logCode, paciente);
        } else {
            Funcionario funcionario = funcionarioRepository.findFuncionarioByEmail(username);
            if (funcionario != null) {
                user = funcionario;
                logger.info("{} - usuario encontrado eh um funcionario ", logCode);
            }
        }

        if (user == null) {
            logger.error("{} - usuario do email {} nao encontrado ", logCode, username);
            throw new UsernameNotFoundException("Email not found");
        }

        logger.info("{} - finalizacao da busca para autenticacao do usuario de email {}  ", logCode, username);
        return user;
    }

    @Transactional
    public void populaTabelaDeRoles() {
        String logCode = "populaTabelaDeRoles()";
        logger.info("{} - percorrendo enum de nivel de permissao {}", logCode, NivelPermissaoEnum.values());

        for (NivelPermissaoEnum itemNivelPermissao : NivelPermissaoEnum.values()) {
            try {

                var role = roleRepository.findRoleByAuthority(itemNivelPermissao.name());

                if (role == null) {
                    Role roleEntity = new Role();
                    roleEntity.setAuthority(itemNivelPermissao.name());
                    roleRepository.save(roleEntity);
                }

            } catch (Exception e) {
                logger.error("{} - algo deu errado ao preencher a tabela de roles {}", logCode, e.getMessage());
            }
        }

        logger.info("{} - tabela de nivel de permissao (roles) populada com sucesso {}", logCode, NivelPermissaoEnum.values());
    }


    public void criaUsuariomaster() {
        String logCode = "criaUsuariomaster()";
        logger.info("{} - criando usuario master {}", logCode, NivelPermissaoEnum.ROLE_MASTER.name());

        try {

            var masterJaCriado = verificaMasterJaCadastrado(userMaster.getEmail());

            if (masterJaCriado != true) {
                logger.info("{} - usuario master ainda nao cadastrado {}", logCode, userMaster.getEmail());
                cadastraUsuarioMaster();
            } else {
                logger.info("{} - usuario master ja cadastrado {}", logCode, userMaster.getEmail());
            }

        } catch (Exception e) {
            logger.error("{} - erro ao cadastrar usuario master , possivel cadadastrar {}", logCode, e.getMessage());
        }

    }

    @Transactional(readOnly = true)
    public Boolean verificaMasterJaCadastrado(String email) {
        var funcionario = funcionarioRepository.findFuncionarioByEmail(email);

        Boolean masterEncontrado = false;

        if (funcionario != null) {
            masterEncontrado = true;
        }

        return masterEncontrado;
    }


    @Transactional
    public void cadastraUsuarioMaster() {

        String logCode = "cadastraUsuarioMaster()";
        logger.info("{} - criando usuario master {}", logCode, NivelPermissaoEnum.ROLE_MASTER.name());

        Funcionario funcionario = new Funcionario();

        funcionario.setIdFuncionario(UUID.randomUUID().toString());
        funcionario.setEmail(userMaster.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senha = userMaster.getPassword();
        var senhaCriptografada = passwordEncoder.encode(senha);
        funcionario.setSenha(senhaCriptografada);

        Role role = roleRepository.findRoleByAuthority(NivelPermissaoEnum.ROLE_MASTER.name());
        funcionario.getRoles().add(role);
        funcionario.setCpf("999-999-999-99");
        funcionario.setCrm("");
        funcionario.setPrimeiroNome("usermaster");
        funcionario.setNomeCompleto("user master application hmv");
        funcionario.setDataNascimento(LocalDate.of(1993, 10, 02));

        EnderecoAdministrativo endereco = new EnderecoAdministrativo();
        endereco.setCodigoEndereco(UUID.randomUUID().toString());
        endereco.setDescricao("endereco da matriz");
        endereco.setLogradouro("R. Ramiro Barcelos");
        endereco.setNumero(910);
        endereco.setComplemento("predio cinza");
        endereco.setCidade("Porto Alegre");
        endereco.setUf("RS");
        endereco.setCep(90035000);

        funcionario.setEndereco(endereco);
        funcionario.setCodigoGrupoFuncao(GrupoFuncaoFuncionarioEnum.MASTER.getCodigoGrupoFuncaoFuncionario());
        funcionario.setCodigoStatusFuncionario(StatusFuncionarioEnum.ATIVO.getCodigoStatusFuncionario());

        Telefone telefone = new Telefone();
        telefone.setDescricao("telefone user master");
        telefone.setCodigoPais(55);
        telefone.setCodigoArea(11);
        telefone.setNumero(973801014);

        funcionario.setTelefone(telefone);
        funcionario.setCodigoGeneroPessoa(GeneroPessoasEnum.MASCULINO.getCodigoGeneroPessoa());

        funcionarioRepository.save(funcionario);
        logger.info("{} - usuario master criado com sucesso {}", logCode, NivelPermissaoEnum.ROLE_MASTER.name());

    }
}
