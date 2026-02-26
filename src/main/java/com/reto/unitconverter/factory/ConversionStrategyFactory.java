package com.reto.unitconverter.factory;

import com.reto.unitconverter.exception.UnsupportedConversionTypeException;
import com.reto.unitconverter.strategy.ConversionStrategy;
import com.reto.unitconverter.strategy.currency.CurrencyConversionStrategy;
import com.reto.unitconverter.strategy.length.LengthConversionStrategy;
import com.reto.unitconverter.strategy.temperature.TemperatureConversionStrategy;
import com.reto.unitconverter.strategy.weight.WeightConversionStrategy;
import org.springframework.stereotype.Component;

@Component
public class ConversionStrategyFactory {

    private final LengthConversionStrategy lengthStrategy;
    private final WeightConversionStrategy weightStrategy;
    private final TemperatureConversionStrategy temperatureStrategy;
    private final CurrencyConversionStrategy currencyStrategy;

    public ConversionStrategyFactory(
            LengthConversionStrategy lengthStrategy,
            WeightConversionStrategy weightStrategy,
            TemperatureConversionStrategy temperatureStrategy,
            CurrencyConversionStrategy currencyStrategy
    ) {
        this.lengthStrategy      = lengthStrategy;
        this.weightStrategy      = weightStrategy;
        this.temperatureStrategy = temperatureStrategy;
        this.currencyStrategy    = currencyStrategy;
    }


    public ConversionStrategy resolve(String type) {
        try {
            ConversionType conversionType = ConversionType.valueOf(type.toUpperCase());
            return switch (conversionType) {
                case LENGTH      -> lengthStrategy;
                case WEIGHT      -> weightStrategy;
                case TEMPERATURE -> temperatureStrategy;
                case CURRENCY    -> currencyStrategy;
            };
        } catch (IllegalArgumentException e) {
            throw new UnsupportedConversionTypeException(type);
        }
    }
}