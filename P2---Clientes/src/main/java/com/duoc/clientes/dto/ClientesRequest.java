package com.duoc.clientes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

    @Data
    public class ClientesRequest {
        @NotBlank(message = "el nombre no puede estar vacío")
        private String nombre;

        @Positive
        @NotNull (message = "el numero no puede ser nulo")
        private int numero;
}
