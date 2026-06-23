package com.duoc.pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PedidosRequest {
    @NotBlank(message = "el pedido no puede estar vacío")
    private String pedido;
}