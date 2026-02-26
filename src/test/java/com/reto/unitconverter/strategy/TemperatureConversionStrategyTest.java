package com.reto.unitconverter.strategy;

import com.reto.unitconverter.exception.UnsupportedUnitException;
import com.reto.unitconverter.strategy.temperature.TemperatureConversionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("TemperatureConversionStrategy Tests")
class TemperatureConversionStrategyTest {

    private TemperatureConversionStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new TemperatureConversionStrategy();
    }

    @Test
    @DisplayName("0°C debe ser 32°F")
    void zero_celsius_to_fahrenheit() {
        assertThat(strategy.convert("CELSIUS", "FAHRENHEIT", 0.0)).isEqualTo(32.0);
    }

    @Test
    @DisplayName("100°C debe ser 212°F")
    void hundred_celsius_to_fahrenheit() {
        assertThat(strategy.convert("CELSIUS", "FAHRENHEIT", 100.0)).isEqualTo(212.0);
    }

    @Test
    @DisplayName("32°F debe ser 0°C")
    void freezing_fahrenheit_to_celsius() {
        assertThat(strategy.convert("FAHRENHEIT", "CELSIUS", 32.0)).isEqualTo(0.0);
    }

    @Test
    @DisplayName("Unidad no soportada debe lanzar excepción")
    void unsupported_unit_throws_exception() {
        assertThatThrownBy(() -> strategy.convert("KELVIN", "CELSIUS", 273.0))
                .isInstanceOf(UnsupportedUnitException.class);
    }
}