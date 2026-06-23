package com.duoc.pedidos.config;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestión de Pedidos")
                        .description("Gestión de Pedidos...")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("DUOC UC - Formativa")
                                .url("https://www.duoc.cl")));
    }
}
