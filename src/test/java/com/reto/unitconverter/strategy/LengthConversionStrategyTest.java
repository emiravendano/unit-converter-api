package com.reto.unitconverter.strategy;

import com.reto.unitconverter.exception.UnsupportedUnitException;
import com.reto.unitconverter.strategy.length.LengthConversionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("LengthConversionStrategy Tests")
class LengthConversionStrategyTest {

    private LengthConversionStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new LengthConversionStrategy();
    }

    @Test
    @DisplayName("1 metro debe ser igual a ~39.3701 pulgadas")
    void meters_to_inches() {
        double result = strategy.convert("METERS", "INCHES", 1.0);
        assertThat(result).isCloseTo(39.3701, within(0.001));
    }

    @Test
    @DisplayName("1 metro debe ser igual a ~3.28084 pies")
    void meters_to_feet() {
        double result = strategy.convert("METERS", "FEET", 1.0);
        assertThat(result).isCloseTo(3.28084, within(0.001));
    }

    @Test
    @DisplayName("Misma unidad origen y destino debe retornar el mismo valor")
    void same_unit_returns_same_value() {
        double result = strategy.convert("METERS", "METERS", 5.0);
        assertThat(result).isEqualTo(5.0);
    }

    @Test
    @DisplayName("Unidad no soportada debe lanzar UnsupportedUnitException")
    void unsupported_unit_throws_exception() {
        assertThatThrownBy(() -> strategy.convert("KILOMETERS", "FEET", 1.0))
                .isInstanceOf(UnsupportedUnitException.class)
                .hasMessageContaining("KILOMETERS");
    }

    @Test
    @DisplayName("supportsUnit debe retornar true para unidades válidas")
    void supports_valid_units() {
        assertThat(strategy.supportsUnit("METERS")).isTrue();
        assertThat(strategy.supportsUnit("INCHES")).isTrue();
        assertThat(strategy.supportsUnit("FEET")).isTrue();
        assertThat(strategy.supportsUnit("meters")).isTrue(); // case-insensitive
    }
}