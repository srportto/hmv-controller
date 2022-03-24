package br.com.hmv.configurations.components;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ClientSecret {
    private String clientSecret;

    //? Essa classe eh usada para instanciar uma secret/senha de um "user" client da aplicacao em questao, ou seja, a senha de um usuario de aplicacao autorizado a usar essa aplicacao
    public ClientSecret(@Value("${security.oauth2.client.client-secret}") String clientSecret) {
        this.clientSecret = clientSecret;
    }
}