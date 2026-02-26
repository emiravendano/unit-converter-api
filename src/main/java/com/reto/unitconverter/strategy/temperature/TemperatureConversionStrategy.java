package com.reto.unitconverter.strategy.temperature;

import com.reto.unitconverter.exception.UnsupportedUnitException;
import com.reto.unitconverter.strategy.ConversionStrategy;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TemperatureConversionStrategy implements ConversionStrategy {

    private static final Set<String> SUPPORTED_UNITS = Set.of("CELSIUS", "FAHRENHEIT");

    @Override
    public double convert(String from, String to, double value) {
        validateUnit(from);
        validateUnit(to);

        String fromUpper = from.toUpperCase();
        String toUpper   = to.toUpperCase();

        // Si son iguales, devolver el mismo valor
        if (fromUpper.equals(toUpper)) return value;

        return switch (fromUpper) {
            case "CELSIUS"    -> celsiusToFahrenheit(value);
            case "FAHRENHEIT" -> fahrenheitToCelsius(value);
            default -> throw new UnsupportedUnitException(from, "TEMPERATURE");
        };
    }

    @Override
    public boolean supportsUnit(String unit) {
        return SUPPORTED_UNITS.contains(unit.toUpperCase());
    }

    private void validateUnit(String unit) {
        if (!supportsUnit(unit)) {
            throw new UnsupportedUnitException(unit, "TEMPERATURE");
        }
    }

    // Fórmula: °F = (°C × 9/5) + 32
    private double celsiusToFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32;
    }

    // Fórmula: °C = (°F - 32) × 5/9
    private double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5.0 / 9.0;
    }
}