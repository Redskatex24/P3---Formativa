package com.duoc.categoriaproducto.repository;

import com.duoc.categoriaproducto.model.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Integer> {
    List<CategoriaModel> findCategoriaModelByNombreContainsIgnoreCase(String nombre);
}
