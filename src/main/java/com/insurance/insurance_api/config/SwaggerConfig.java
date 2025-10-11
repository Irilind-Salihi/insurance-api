package com.insurance.insurance_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig
{
    @Bean
    public OpenAPI openApiConfig(){
        return new OpenAPI()
                .info(new Info()
                        .title("Insurance API")
                        .version("1.0.0")
                        .description("API for managing clients and contracts")
                        .contact(new Contact()
                                .name("Irilind Salihi")
                                .email("irilind.salihi.test@gmail.com")
                        )
                );
    }
}
