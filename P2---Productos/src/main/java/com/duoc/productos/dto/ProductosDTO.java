package com.duoc.productos.dto;

import lombok.Data;

@Data
public class ProductosDTO {
    private int id;
    private String nombre;
    private double precio;
    private Integer id_categoria;
    private String nombreCategoria;
}
