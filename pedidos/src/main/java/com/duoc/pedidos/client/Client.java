package com.duoc.pedidos.client;

import com.duoc.pedidos.model.ClientModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "cliente", url = "http://localhost:8080/api/v1")
public interface Client {

    @GetMapping("/clientes")
    List<ClientModel> obtenerClientes();

    @GetMapping("/clientes/{id}")
    ClientModel obtenerClientes(@PathVariable Integer id);
}
