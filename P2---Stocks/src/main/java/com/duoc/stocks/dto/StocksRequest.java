package com.duoc.stocks.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class StocksRequest {
    @Positive
    @NotNull(message = "el numero no puede ser nulo")
    private int cantidad;
}
