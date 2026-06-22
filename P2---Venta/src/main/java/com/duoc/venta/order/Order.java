package com.duoc.venta.order;

import com.duoc.venta.model.OrderModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "pedidos", url = "http://localhost:8088/api/v1")
public interface Order {

    @GetMapping("/pedidos")
    List<OrderModel> obtenerPedidos();

    @GetMapping("/productos/{id}")
    OrderModel obtenerPedidos(@PathVariable Integer id);
}
