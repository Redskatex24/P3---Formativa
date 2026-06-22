package com.duoc.venta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ventas")
public class VentaModel {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(nullable = false)
        private String venta;

        @Column(nullable = false)
        private int id_cliente;

        @Column(nullable = false)
        private int id_producto;

        @Column(nullable = false)
        private int id_vendedor;

        @Column(nullable = false)
        private int id_pedido;
}
