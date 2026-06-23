package com.duoc.venta.exception;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException(int id) {
        super("Pedido no encontrado con id: " + id);
    }
}
