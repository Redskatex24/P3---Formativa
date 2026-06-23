package com.duoc.pago.repository;

import com.duoc.pago.model.PagoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<PagoModel, Integer> {
    List<PagoModel> findPagoModelByTipoContainsIgnoreCase(String tipo);
}