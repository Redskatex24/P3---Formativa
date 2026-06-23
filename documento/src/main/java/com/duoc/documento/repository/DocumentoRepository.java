package com.duoc.documento.repository;

import com.duoc.documento.model.DocumentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<DocumentoModel, Integer> {
    List<DocumentoModel> findDocumentoModelByTipoContainsIgnoreCase(String tipo);
}
