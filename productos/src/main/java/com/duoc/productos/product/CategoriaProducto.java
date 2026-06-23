package com.duoc.productos.product;

import com.duoc.productos.model.CategoriasModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "categoria-producto", url = "http://localhost:8083/api/v1")
public interface CategoriaProducto {

    @GetMapping("/categorias")
    List<CategoriasModel> obtenerCategorias();

    @GetMapping("/categorias/{id}")
    CategoriasModel obtenerCategorias(@PathVariable Integer id);
}