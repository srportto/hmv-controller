package br.com.hmv.scheduled;

import br.com.hmv.services.AuthService;
import br.com.hmv.services.EmergenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final EmergenciaService emergenciaService;
    private final AuthService authService;

    @Autowired
    public ScheduledTasks(EmergenciaService emergenciaService, AuthService authService) {
        this.emergenciaService = emergenciaService;
        this.authService = authService;

        this.emergenciaService.populaTabelaDeDor();
        this.authService.populaTabelaDeRoles();
        this.authService.criaUsuariomaster();

        this.syncTabelaRegiaoEscalaDor();
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void syncTabelaRegiaoEscalaDor() {
        //TODO - implementar
    }
}
