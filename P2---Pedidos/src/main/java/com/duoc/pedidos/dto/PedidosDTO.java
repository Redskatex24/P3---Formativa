package com.duoc.pedidos.dto;

import lombok.Data;

@Data
public class PedidosDTO {
    private int id;
    private String pedido;
    private int id_cliente;
    private String nombre_cliente;
    private int id_producto;
    private String nombre_producto;
    private double precio_producto;
}
