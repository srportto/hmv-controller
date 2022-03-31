package br.com.hmv.configurations.components;

import br.com.hmv.repositories.FuncionarioRepository;
import br.com.hmv.repositories.PacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// Essa classe serve para adicionar informacoes a um token
@Component
@AllArgsConstructor
public class JwtTokenEnhancer implements TokenEnhancer {

    private PacienteRepository pacienteRepository;
    private FuncionarioRepository funcionarioRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Boolean isFuncionario = false;
        //String cpf = "";
        String primeiroNome = "";
        String idUserAutenticado = "";

        var emailAutenticado = authentication.getName();

        var pacienteUser = pacienteRepository.findPacienteByEmail(emailAutenticado);
        if (pacienteUser != null) {
            //cpf = pacienteUser.getCpf();
            primeiroNome = pacienteUser.getPrimeiroNome();
            idUserAutenticado = pacienteUser.getIdPaciente();
        } else {
            var funcionarioUser = funcionarioRepository.findFuncionarioByEmail(emailAutenticado);
            if (funcionarioUser != null) {
                isFuncionario = true;
                //cpf = funcionarioUser.getCpf();
                primeiroNome = funcionarioUser.getPrimeiroNome();
                idUserAutenticado = funcionarioUser.getIdFuncionario();

            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("funcionario", isFuncionario);
        //map.put("cpf", cpf);
        map.put("primeiro_nome", primeiroNome);
        map.put("id_user", idUserAutenticado);

        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken; //exemplo de down casting
        token.setAdditionalInformation(map);

        return accessToken;
    }
}
