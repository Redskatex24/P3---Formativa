package com.duoc.documento.service;

import com.duoc.documento.dto.DocumentoDTO;
import com.duoc.documento.dto.DocumentoRequest;
import com.duoc.documento.exception.DocumentoNotFoundException;
import com.duoc.documento.model.DocumentoModel;
import com.duoc.documento.repository.DocumentoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DocumentoService {
    @Autowired
    private DocumentoRepository documentoRepository;

    public DocumentoDTO guardar(DocumentoRequest request) {
        log.info("Iniciando el proceso para guardar un nuevo documento de tipo: {}", request.getTipo());
        DocumentoModel documento = new DocumentoModel();
        documento.setTipo(request.getTipo());

        DocumentoModel guardado = documentoRepository.save(documento);
        log.info("Documento guardado exitosamente con ID: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public List<DocumentoDTO> listar() {
        log.info("Solicitando el listado completo de documentos desde la base de datos.");
        return documentoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public DocumentoDTO buscarPorId(int id) {
        log.info("Buscando documento por ID: {}", id);
        DocumentoModel clientes = documentoRepository.findById(id).orElseThrow(() -> new DocumentoNotFoundException(id));
        return convertirADTO(clientes);
    }

    public DocumentoDTO actualizar(int id, DocumentoRequest request) {
        log.info("Iniciando actualización en el documento ID: {}. Nuevo tipo asignado: {}", id, request.getTipo());
        DocumentoModel documentoExistente = documentoRepository.findById(id).orElseThrow(() -> new DocumentoNotFoundException(id));
        documentoExistente.setTipo(request.getTipo());

        DocumentoModel actualizado = documentoRepository.save(documentoExistente);
        log.info("Documento ID: {} modificado con éxito en el sistema.", id);
        return convertirADTO(actualizado);
    }

    public void eliminar(int id) {
        log.info("Ejecutando la eliminación en la base de datos para el documento ID: {}", id);
        documentoRepository.deleteById(id);
    }

    private DocumentoDTO convertirADTO(DocumentoModel documento) {
        if (documento == null) return null;
        DocumentoDTO dto = new DocumentoDTO();
        dto.setId(documento.getId());
        dto.setTipo(documento.getTipo());
        return dto;
    }
    public List<DocumentoDTO> buscarPorTipo(String tipo) {
        log.info("Filtrando documentos que contengan el tipo: '{}'", tipo);
        return documentoRepository.findDocumentoModelByTipoContainsIgnoreCase(tipo)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
