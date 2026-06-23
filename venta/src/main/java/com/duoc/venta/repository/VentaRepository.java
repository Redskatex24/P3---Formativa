package com.duoc.venta.repository;

import com.duoc.venta.model.VentaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<VentaModel, Integer> {
    List<VentaModel> findVentaModelByVentaContainsIgnoreCase(String venta);
}
