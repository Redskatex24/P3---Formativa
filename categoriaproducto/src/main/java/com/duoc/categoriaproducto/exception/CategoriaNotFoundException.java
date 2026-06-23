package com.duoc.categoriaproducto.exception;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(Integer id) {
        super("Cliente no encontrado con id: " + id);
    }
}
