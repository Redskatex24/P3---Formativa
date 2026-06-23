package com.duoc.venta.exception;

public class VentaNotFoundException extends RuntimeException {
    public VentaNotFoundException(int id) {
        super("Venta no encontrada con id: " + id);
    }
}
