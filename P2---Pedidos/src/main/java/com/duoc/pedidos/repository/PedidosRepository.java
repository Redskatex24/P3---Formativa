package com.duoc.pedidos.repository;

import com.duoc.pedidos.model.PedidosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidosRepository extends JpaRepository<PedidosModel, Integer> {
    List<PedidosModel> findPedidosModelByPedidoContainsIgnoreCase(String pedido);
}
