package com.reto.unitconverter.strategy.weight;

import com.reto.unitconverter.exception.UnsupportedUnitException;
import com.reto.unitconverter.strategy.ConversionStrategy;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class WeightConversionStrategy implements ConversionStrategy {

    private static final Set<String> SUPPORTED_UNITS = Set.of("KILOGRAMS", "POUNDS", "OUNCES");

    private static final double POUND_TO_KG = 0.45359237;
    private static final double OUNCE_TO_KG = 0.02834952;

    @Override
    public double convert(String from, String to, double value) {
        validateUnit(from);
        validateUnit(to);

        double inKg = toKilograms(from.toUpperCase(), value);
        return fromKilograms(to.toUpperCase(), inKg);
    }

    @Override
    public boolean supportsUnit(String unit) {
        return SUPPORTED_UNITS.contains(unit.toUpperCase());
    }

    private void validateUnit(String unit) {
        if (!supportsUnit(unit)) {
            throw new UnsupportedUnitException(unit, "WEIGHT");
        }
    }

    private double toKilograms(String unit, double value) {
        return switch (unit) {
            case "KILOGRAMS" -> value;
            case "POUNDS"    -> value * POUND_TO_KG;
            case "OUNCES"    -> value * OUNCE_TO_KG;
            default -> throw new UnsupportedUnitException(unit, "WEIGHT");
        };
    }

    private double fromKilograms(String unit, double kg) {
        return switch (unit) {
            case "KILOGRAMS" -> kg;
            case "POUNDS"    -> kg / POUND_TO_KG;
            case "OUNCES"    -> kg / OUNCE_TO_KG;
            default -> throw new UnsupportedUnitException(unit, "WEIGHT");
        };
    }
}