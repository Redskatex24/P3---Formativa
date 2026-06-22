package com.duoc.clientes.exeption;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(Integer id_categoria) {
        super("Categoria no encontrada con id: " + id_categoria);
    }
}
