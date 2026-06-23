package com.duoc.categoriaproducto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaRequest {
    @NotBlank(message = "el nombre no puede estar vacío")
    private String nombre;
}
