package com.duoc.productos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductosRequest {
    @NotBlank(message = "el nombre no puede estar vacío")
    private String nombre;

    @Positive
    @NotNull(message = "el precio no puede ser nulo")
    @NotBlank (message = "el precio no puede estar vacío")
    private double precio;
}
