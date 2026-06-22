package com.duoc.vendedores.exception;

public class VendedorNotFoundException extends RuntimeException {
    public VendedorNotFoundException(Integer id) {
        super("Vendedor no encontrado con id: " + id);
    }
}
