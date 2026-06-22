package com.duoc.clientes.exeption;

public class ClientesNotFoundException extends RuntimeException {
    public ClientesNotFoundException(int id) {
        super("Cliente no encontrado con id: " + id);
    }
}