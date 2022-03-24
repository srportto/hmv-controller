package br.com.hmv.configurations.components;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtDuration {
    private Integer jwtDuration;

    //?configuracao do tempo de expiracao de um token em segundos onde 86400 equivale a 24h
    public JwtDuration(@Value("${jwt.duration}") Integer jwtDuration) {
        this.jwtDuration = jwtDuration;
    }
}