package br.com.tads.manutencaoequipamentoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@OpenAPIDefinition
public class ManutencaoEquipamentoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManutencaoEquipamentoApiApplication.class, args);
	}

}
