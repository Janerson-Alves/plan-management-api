package br.com.jaanalves.fase4.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

// Configuração do Swagger
public class OpenApiConfig {

    // Config e info do Swagger titulo, versão e descrição
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Plan Management API")
                        .version("1.0")
                        .description("API RESTful para gestão e provisionamento de planos de telefonia")
                );
    }
}
