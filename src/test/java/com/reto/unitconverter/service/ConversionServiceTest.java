package com.reto.unitconverter.service;

import com.reto.unitconverter.dto.ConversionRequest;
import com.reto.unitconverter.dto.ConversionResponse;
import com.reto.unitconverter.exception.UnsupportedConversionTypeException;
import com.reto.unitconverter.factory.ConversionStrategyFactory;
import com.reto.unitconverter.strategy.ConversionStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ConversionService Tests")
class ConversionServiceTest {

    @Mock
    private ConversionStrategyFactory strategyFactory;

    @Mock
    private ConversionStrategy mockStrategy;

    @InjectMocks
    private ConversionService conversionService;

    @Test
    @DisplayName("Debe retornar ConversionResponse con el valor convertido")
    void convert_returns_correct_response() {
        ConversionRequest request = new ConversionRequest("LENGTH", "METERS", "FEET", 1.0);

        when(strategyFactory.resolve("LENGTH")).thenReturn(mockStrategy);
        when(mockStrategy.convert("METERS", "FEET", 1.0)).thenReturn(3.28084);

        ConversionResponse response = conversionService.convert(request);

        assertThat(response.type()).isEqualTo("LENGTH");
        assertThat(response.from()).isEqualTo("METERS");
        assertThat(response.to()).isEqualTo("FEET");
        assertThat(response.originalValue()).isEqualTo(1.0);
        assertThat(response.convertedValue()).isCloseTo(3.28084, within(0.0001));
    }

    @Test
    @DisplayName("Debe propagar excepción cuando el tipo no está soportado")
    void convert_throws_when_type_unsupported() {
        ConversionRequest request = new ConversionRequest("VOLUME", "LITERS", "GALLONS", 1.0);
        when(strategyFactory.resolve("VOLUME")).thenThrow(new UnsupportedConversionTypeException("VOLUME"));

        assertThatThrownBy(() -> conversionService.convert(request))
                .isInstanceOf(UnsupportedConversionTypeException.class);
    }
}