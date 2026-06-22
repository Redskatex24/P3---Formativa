package com.duoc.vendedores.repository;

import com.duoc.vendedores.model.VendedoresModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendedoresRepository extends JpaRepository<VendedoresModel, Integer> {
    List<VendedoresModel> findVendedoresModelByNombreContainsIgnoreCase(String nombre);
}