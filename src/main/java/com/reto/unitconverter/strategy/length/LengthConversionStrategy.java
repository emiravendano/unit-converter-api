package com.reto.unitconverter.strategy.length;

import com.reto.unitconverter.exception.UnsupportedUnitException;
import com.reto.unitconverter.strategy.ConversionStrategy;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class LengthConversionStrategy implements ConversionStrategy {

    private static final Set<String> SUPPORTED_UNITS = Set.of("METERS", "INCHES", "FEET");

    private static final double INCH_TO_METER = 0.0254;
    private static final double FOOT_TO_METER  = 0.3048;

    @Override
    public double convert(String from, String to, double value) {
        validateUnit(from);
        validateUnit(to);

        double inMeters = toMeters(from.toUpperCase(), value);

        return fromMeters(to.toUpperCase(), inMeters);
    }

    @Override
    public boolean supportsUnit(String unit) {
        return SUPPORTED_UNITS.contains(unit.toUpperCase());
    }

    private void validateUnit(String unit) {
        if (!supportsUnit(unit)) {
            throw new UnsupportedUnitException(unit, "LENGTH");
        }
    }

    private double toMeters(String unit, double value) {
        return switch (unit) {
            case "METERS" -> value;
            case "INCHES" -> value * INCH_TO_METER;
            case "FEET"   -> value * FOOT_TO_METER;
            default -> throw new UnsupportedUnitException(unit, "LENGTH");
        };
    }

    private double fromMeters(String unit, double meters) {
        return switch (unit) {
            case "METERS" -> meters;
            case "INCHES" -> meters / INCH_TO_METER;
            case "FEET"   -> meters / FOOT_TO_METER;
            default -> throw new UnsupportedUnitException(unit, "LENGTH");
        };
    }
}