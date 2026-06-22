package com.duoc.documento.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DocumentoRequest {
    @NotBlank(message = "El tipo no puede estar vacío")
    private String tipo;
}
