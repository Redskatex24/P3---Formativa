package com.duoc.productos.repository;

import com.duoc.productos.model.ProductosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<ProductosModel, Integer> {
    List<ProductosModel> findProductosModelByNombreContainsIgnoreCase(String nombre);
}
