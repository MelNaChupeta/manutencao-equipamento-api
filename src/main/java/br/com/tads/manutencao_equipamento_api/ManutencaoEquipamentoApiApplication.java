package br.com.tads.manutencao_equipamento_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ManutencaoEquipamentoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManutencaoEquipamentoApiApplication.class, args);
	}

}
