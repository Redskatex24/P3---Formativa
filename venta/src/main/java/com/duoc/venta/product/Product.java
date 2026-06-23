package com.duoc.venta.product;

import com.duoc.venta.model.ProductModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "producto", url = "http://localhost:8082/api/v1")
public interface Product {

    @GetMapping("/productos")
    List<ProductModel> obtenerProductos();

    @GetMapping("/productos/{id}")
    ProductModel obtenerProductos(@PathVariable Integer id);
}
