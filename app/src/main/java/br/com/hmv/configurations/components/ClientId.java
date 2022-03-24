package br.com.hmv.configurations.components;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
public class ClientId {
    private String clientId;

    //? Essa classe eh usada para instanciar um "user" client da aplicacao em questao, ou seja, um usuario de aplicacao autorizado a usar essa aplicacao
    public ClientId(@Value("${security.oauth2.client.client-id}") String clientId){
        this.clientId = clientId;
    }
}