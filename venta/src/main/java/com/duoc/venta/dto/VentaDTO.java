package com.duoc.venta.dto;

import lombok.Data;

@Data
public class VentaDTO {
    private int id;
    private String venta;
    private int id_cliente;
    private String nombre_cliente;
    private int id_producto;
    private String nombre_producto;
    private double precio_producto;
    private int id_pedido;
    private String pedido;
    private int id_vendedor;
    private String nombre_vendedor;
}
