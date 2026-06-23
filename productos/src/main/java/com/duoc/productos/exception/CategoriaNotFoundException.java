package com.duoc.productos.exception;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(Integer id_categoria) {
        super("Categoria no encontrada con id: " + id_categoria);
    }
}
