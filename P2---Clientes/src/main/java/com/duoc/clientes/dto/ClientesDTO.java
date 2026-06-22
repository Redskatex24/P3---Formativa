package com.duoc.clientes.dto;

import lombok.Data;

@Data
public class ClientesDTO {
    private int id;
    private String nombre;
    private int numero;
    private Integer id_categoria;
    private String nombreCategoria;
}
