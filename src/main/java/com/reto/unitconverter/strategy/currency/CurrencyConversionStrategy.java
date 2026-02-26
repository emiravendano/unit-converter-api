package com.reto.unitconverter.strategy.currency;

import com.reto.unitconverter.client.ExchangeRateClient;
import com.reto.unitconverter.exception.UnsupportedUnitException;
import com.reto.unitconverter.strategy.ConversionStrategy;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CurrencyConversionStrategy implements ConversionStrategy {

    private static final Set<String> SUPPORTED_CURRENCIES = Set.of("USD", "PEN", "EUR", "GBP", "JPY", "BRL", "MXN");

    private final ExchangeRateClient exchangeRateClient;

    public CurrencyConversionStrategy(ExchangeRateClient exchangeRateClient) {
        this.exchangeRateClient = exchangeRateClient;
    }

    @Override
    public double convert(String from, String to, double value) {
        validateUnit(from);
        validateUnit(to);

        String fromUpper = from.toUpperCase();
        String toUpper   = to.toUpperCase();

        if (fromUpper.equals(toUpper)) return value;

        var rates = exchangeRateClient.getExchangeRates(fromUpper);

        Double rate = rates.get(toUpper);
        if (rate == null) {
            throw new UnsupportedUnitException(to, "CURRENCY");
        }

        return value * rate;
    }

    @Override
    public boolean supportsUnit(String unit) {
        return SUPPORTED_CURRENCIES.contains(unit.toUpperCase());
    }

    private void validateUnit(String unit) {
        if (!supportsUnit(unit)) {
            throw new UnsupportedUnitException(unit, "CURRENCY");
        }
    }
}