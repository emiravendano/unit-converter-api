package com.reto.unitconverter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConversionResponse(
        String type,
        String from,
        String to,
        Double originalValue,
        Double convertedValue,
        String message
) {
    public ConversionResponse(String type, String from, String to, Double originalValue, Double convertedValue) {
        this(type, from, to, originalValue, convertedValue, null);
    }
}