package com.duoc.pedidos.exception;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(int id) {
        super("Producto no encontrado con id: " + id);
    }
}
