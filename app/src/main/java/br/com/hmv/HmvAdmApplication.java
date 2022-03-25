package br.com.hmv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HmvAdmApplication {
    public static void main(String[] args) {
        SpringApplication.run(HmvAdmApplication.class, args);
    }
}
