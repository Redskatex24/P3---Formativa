package com.duoc.venta.exception;

public class VendedorNotFoundException extends RuntimeException {
    public VendedorNotFoundException(int id) {
        super("Vendedor no encontrado con id: " + id);
    }
}