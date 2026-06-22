package com.duoc.venta.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(int id) {
        super("Cliente no encontrado con id: " + id);
    }
}
