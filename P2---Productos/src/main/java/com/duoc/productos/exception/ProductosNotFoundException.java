package com.duoc.productos.exception;

public class ProductosNotFoundException extends RuntimeException {
    public ProductosNotFoundException(int id) {
        super("Producto no encontrado con id: " + id);
    }
}