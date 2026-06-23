package com.duoc.venta.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VentaRequest {
    @NotBlank(message = "la venta no puede estar vacío")
    private String venta;
}
