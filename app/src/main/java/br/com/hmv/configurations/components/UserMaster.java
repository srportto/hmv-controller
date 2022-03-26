package br.com.hmv.configurations.components;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class UserMaster {
    private String email;
    private String password;

    public UserMaster(@Value("${user.master.email}") String email, @Value("${user.master.password}") String password) {
        this.email = email;
        this.password = password;
    }
}