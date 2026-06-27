package com.duoc.categoriacliente.exception;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(Integer id) {
        super("Categoria no encontrada con id: " + id);
    }
}
