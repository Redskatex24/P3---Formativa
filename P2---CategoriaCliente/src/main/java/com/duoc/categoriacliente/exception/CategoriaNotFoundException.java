package com.duoc.categoriacliente.exception;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(Integer id) {
        super("Cliente no encontrado con id: " + id);
    }
}
