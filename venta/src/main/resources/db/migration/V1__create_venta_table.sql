CREATE TABLE ventas (
    id int NOT NULL AUTO_INCREMENT,
    venta VARCHAR(255) NOT NULL,
    id_cliente INT DEFAULT NULL,
    id_producto INT DEFAULT NULL,
    id_pedido INT DEFAULT NULL,
    id_vendedor INT DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;