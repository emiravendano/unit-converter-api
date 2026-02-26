package com.reto.unitconverter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ConversionRequest(

        @NotBlank(message = "El tipo de conversión es obligatorio")
        String type,

        @NotBlank(message = "La unidad de origen es obligatoria")
        String from,

        @NotBlank(message = "La unidad de destino es obligatoria")
        String to,

        @NotNull(message = "El valor a convertir es obligatorio")
        @Positive(message = "El valor debe ser mayor que cero")
        Double value
) {}