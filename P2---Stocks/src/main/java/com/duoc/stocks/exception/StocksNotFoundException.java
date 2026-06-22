package com.duoc.stocks.exception;

public class StocksNotFoundException extends RuntimeException {
    public StocksNotFoundException(int id) {
        super("Cliente no encontrado con id: " + id);
    }
}
