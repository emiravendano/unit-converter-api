package com.reto.unitconverter.client;

import com.reto.unitconverter.dto.ExchangeRateApiResponse;
import com.reto.unitconverter.exception.ExternalApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Component
public class ExchangeRateClient {

    private final WebClient webClient;
    private final String apiKey;

    public ExchangeRateClient(
            WebClient.Builder webClientBuilder,
            @Value("${exchange.api.base-url}") String baseUrl,
            @Value("${exchange.api.key}") String apiKey
    ) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public Map<String, Double> getExchangeRates(String baseCurrency) {
        try {
            ExchangeRateApiResponse response = webClient.get()
                    .uri("/{apiKey}/latest/{baseCurrency}", apiKey, baseCurrency)
                    .retrieve()
                    .bodyToMono(ExchangeRateApiResponse.class)
                    .block(); // Bloqueante intencionalmente (API síncrona REST)

            if (response == null || !"success".equals(response.result())) {
                String errorDetail = response != null ? response.errorType() : "respuesta nula";
                throw new ExternalApiException(
                        "La API de cambio de moneda retornó un error: " + errorDetail
                );
            }

            return response.conversionRates();

        } catch (WebClientResponseException ex) {
            throw new ExternalApiException(
                    "Error al conectar con la API de cambio de moneda (HTTP " + ex.getStatusCode() + "): "
                            + ex.getMessage(), ex
            );
        } catch (ExternalApiException ex) {
            throw ex; // Re-lanzar las propias sin envolver
        } catch (Exception ex) {
            throw new ExternalApiException(
                    "Error inesperado al consultar tasas de cambio: " + ex.getMessage(), ex
            );
        }
    }
}