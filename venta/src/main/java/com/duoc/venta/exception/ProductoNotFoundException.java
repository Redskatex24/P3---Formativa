package com.duoc.venta.exception;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(int id) {
        super("Producto no encontrado con id: " + id);
    }
}
