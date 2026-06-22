package com.duoc.clientes.client;

import com.duoc.clientes.model.CategoriasModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "categoria-cliente", url = "http://localhost:8090/api/v1")
public interface CategoriaCliente {

    @GetMapping("/categorias")
    List<CategoriasModel> obtenerCategorias();

    @GetMapping("/categorias/{id}")
    CategoriasModel obtenerCategorias(@PathVariable Integer id);
}
