package br.com.hmv.configurations;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableResourceServer
@AllArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private Environment env;    // ambiente de execução da aplicação
    private JwtTokenStore tokenStore;

    //? rotas publicas
    private static final String[] PUBLIC = {"/oauth/token", "/h2-console/**"};

    //? Rotas liberadas para GET quando autenticados
    private static final String[] TODOS_OS_AUTENTICADOS = {
            "/api/emergencias/**",
            "/api/especialidades/**",
            "/api/eventos_traumaticos/**",
            "/api/funcionarios/**",
            "/api/hospitais/**",
            "/api/convenios/**",
            "/api/habitos/**",
            "/api/pacientes/**",
            "/api/sintomas/**"};


    //? Rotas liberadas para GET quando autenticados
    private static final String[] ADMINISTRATIVO_AUTENTICADOS = {
            "/api/emergencias/**",
            "/api/especialidades/**",
            "/api/eventos_traumaticos/**",
            "/api/funcionarios/**",
            "/api/habitos/**",
            "/api/pacientes/**",
            "/api/sintomas/**"};


    //? Rotas liberadas somente para o perfils PACIENTE
    private static final String[] PACIENTE_AUTENTICADO = {"/api/emergencias/**", "/api/pacientes/**"};

    //? Rotas liberadas somente para o perfils PACIENTE
    private static final String[] PACIENTE_PUBLIC = {"/api/pacientes/**"};

    //? Rota liberada somente para o perfil ADMIN
    private static final String[] ADMIN = {"/api/hospitais/**", "/api/convenios/**"};

    //? Esse metodo validara se o token eh valido para acessar o recurso
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    //? Esse metodo
    @Override
    public void configure(HttpSecurity http) throws Exception {

        //? Caso a lista de profiles ativos contenha o profile "test"
        //? entao deve-se liberar o H2
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.authorizeRequests()
                .antMatchers(PUBLIC).permitAll()
                .antMatchers(HttpMethod.POST, PACIENTE_PUBLIC).permitAll()
                .antMatchers(HttpMethod.GET, TODOS_OS_AUTENTICADOS).hasAnyRole("ADMINISTRATIVO", "MASTER", "PACIENTE")
                .antMatchers(ADMINISTRATIVO_AUTENTICADOS).hasAnyRole("ADMINISTRATIVO", "MASTER")
                .antMatchers(ADMIN).hasRole("MASTER")
                .antMatchers(PACIENTE_AUTENTICADO).hasRole("PACIENTE")
                .anyRequest().authenticated();
    }
}
