package com.duoc.pago.exception;

public class PagoNotFoundException extends RuntimeException {
    public PagoNotFoundException(Integer id) {
        super("Pago no encontrado con id: " + id);
    }
}
