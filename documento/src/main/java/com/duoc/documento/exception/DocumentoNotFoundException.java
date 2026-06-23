package com.duoc.documento.exception;

public class DocumentoNotFoundException extends RuntimeException {
    public DocumentoNotFoundException(Integer id) {
        super("Pago no encontrado con id: " + id);
    }
}
