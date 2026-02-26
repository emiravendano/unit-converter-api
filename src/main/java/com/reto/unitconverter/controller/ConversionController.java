package com.reto.unitconverter.controller;

import com.reto.unitconverter.dto.ConversionRequest;
import com.reto.unitconverter.dto.ConversionResponse;
import com.reto.unitconverter.service.ConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "Conversión de Unidades", description = "API para convertir entre unidades de longitud, peso, temperatura y moneda")
public class ConversionController {

    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping("/convert")
    @Operation(
            summary = "Convertir unidades",
            description = "Convierte un valor de una unidad a otra según el tipo especificado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conversión exitosa"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos o unidad no soportada"),
            @ApiResponse(responseCode = "503", description = "Error al consultar la API externa de monedas")
    })
    public ResponseEntity<ConversionResponse> convert(
            @Parameter(description = "Tipo de conversión: LENGTH, WEIGHT, TEMPERATURE, CURRENCY", example = "LENGTH")
            @RequestParam @NotBlank String type,

            @Parameter(description = "Unidad de origen", example = "METERS")
            @RequestParam @NotBlank String from,

            @Parameter(description = "Unidad de destino", example = "FEET")
            @RequestParam @NotBlank String to,

            @Parameter(description = "Valor a convertir (debe ser positivo)", example = "10.0")
            @RequestParam @Positive Double value
    ) {
        ConversionRequest request = new ConversionRequest(type, from, to, value);
        ConversionResponse response = conversionService.convert(request);
        return ResponseEntity.ok(response);
    }
}