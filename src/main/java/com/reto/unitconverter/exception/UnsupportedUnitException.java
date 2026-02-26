package com.reto.unitconverter.exception;

public class UnsupportedUnitException extends RuntimeException{
    public UnsupportedUnitException(String unit, String type){
        super("Unidad no soportada: '"+unit+"' para el tipo "+type+"'");
    }
}
