package com.duoc.pedidos.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(int id) {
        super("Cliente no encontrado con id: " + id);
    }
}
