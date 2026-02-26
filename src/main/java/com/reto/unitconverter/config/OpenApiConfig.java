package com.reto.unitconverter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Conversión de Unidades")
                        .version("1.0.0")
                        .description("""
                    Servicio REST para convertir entre unidades de:
                    - **Longitud**: METERS, INCHES, FEET
                    - **Peso**: KILOGRAMS, POUNDS, OUNCES
                    - **Temperatura**: CELSIUS, FAHRENHEIT
                    - **Moneda**: USD, PEN, EUR, GBP, JPY (via ExchangeRate-API)
                    """)
                        .contact(new Contact()
                                .name("Reto Técnico Backend Junior")
                                .email("dev@example.com"))
                );
    }
}