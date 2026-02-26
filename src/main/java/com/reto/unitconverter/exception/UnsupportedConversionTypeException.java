package com.reto.unitconverter.exception;

public class UnsupportedConversionTypeException extends RuntimeException{
    public UnsupportedConversionTypeException(String type){
        super("Tipo de conversión no soportado: '"+type+"'. Tipos válidos: LENGTH, WIGHT, TEMPERATURE, CURRENCY");
    }
}
