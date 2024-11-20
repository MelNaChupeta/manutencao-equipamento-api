package br.com.tads.manutencaoequipamentoapi.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SpringDocConfiguration {

    final String securitySchemeName = "bearerAuth";

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API manutencao equipamento")
                        .description("API criada para o app manutenção equipamentos")
                        .version("v0.0.1")
                        .license(new License().name("Mel na Chupeta, todos os direitos reservados.")
                                .url("https://www.melnachupeta.com.br/")))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .in(In.HEADER)
                                                .name("Authorization")))
                         .addServersItem(new Server().url("http://localhost:8080/manutencao-equipamento-api"));
    }
}
