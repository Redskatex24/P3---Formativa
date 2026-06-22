package com.duoc.vendedores.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VendedoresRequest {
    @NotBlank(message = "el nombre no puede estar vacío")
    private String nombre;
}
