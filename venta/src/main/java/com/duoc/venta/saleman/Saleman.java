package com.duoc.venta.saleman;

import com.duoc.venta.model.SalemanModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "vendedor", url = "http://localhost:8085/api/v1")
public interface Saleman {

    @GetMapping("/vendedores")
    List<SalemanModel> obtenerlistaVendedores();

    @GetMapping("/vendedores/{id}")
    SalemanModel obtenerVendedores(@PathVariable Integer id);
}
