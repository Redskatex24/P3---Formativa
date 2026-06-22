package com.duoc.pago.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PagoRequest {
    @NotBlank(message = "El tipo no puede estar vacío")
    private String tipo;
}

