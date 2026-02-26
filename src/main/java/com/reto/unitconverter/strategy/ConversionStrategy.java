package com.reto.unitconverter.strategy;

public interface ConversionStrategy {

    double convert(String from, String to, double value);

    boolean supportsUnit(String unit);
}