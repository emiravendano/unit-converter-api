package com.reto.unitconverter.service;

import com.reto.unitconverter.dto.ConversionRequest;
import com.reto.unitconverter.dto.ConversionResponse;
import com.reto.unitconverter.factory.ConversionStrategyFactory;
import com.reto.unitconverter.strategy.ConversionStrategy;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    private final ConversionStrategyFactory strategyFactory;

    public ConversionService(ConversionStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public ConversionResponse convert(ConversionRequest request) {

        ConversionStrategy strategy = strategyFactory.resolve(request.type());

        double result = strategy.convert(request.from(), request.to(), request.value());

        double rounded = Math.round(result * 1_000_000.0) / 1_000_000.0;

        return new ConversionResponse(
                request.type().toUpperCase(),
                request.from().toUpperCase(),
                request.to().toUpperCase(),
                request.value(),
                rounded
        );
    }
}