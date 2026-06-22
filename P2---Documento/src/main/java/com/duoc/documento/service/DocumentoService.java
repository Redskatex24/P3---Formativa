package com.duoc.documento.service;

import com.duoc.documento.dto.DocumentoDTO;
import com.duoc.documento.dto.DocumentoRequest;
import com.duoc.documento.exception.DocumentoNotFoundException;
import com.duoc.documento.model.DocumentoModel;
import com.duoc.documento.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoService {
    @Autowired
    private DocumentoRepository documentoRepository;

    public DocumentoDTO guardar(DocumentoRequest request) {
        DocumentoModel documento = new DocumentoModel();
        documento.setTipo(request.getTipo());

        DocumentoModel guardado = documentoRepository.save(documento);
        return convertirADTO(guardado);
    }

    public List<DocumentoDTO> listar() {
        return documentoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public DocumentoDTO buscarPorId(int id) {
        DocumentoModel clientes = documentoRepository.findById(id).orElseThrow(() -> new DocumentoNotFoundException(id));
        return convertirADTO(clientes);
    }

    public DocumentoDTO actualizar(int id, DocumentoRequest request) {
        DocumentoModel documentoExistente = documentoRepository.findById(id).orElseThrow(() -> new DocumentoNotFoundException(id));
        documentoExistente.setTipo(request.getTipo());

        DocumentoModel actualizado = documentoRepository.save(documentoExistente);
        return convertirADTO(actualizado);
    }

    public void eliminar(int id) {
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
        return documentoRepository.findDocumentoModelByTipoContainsIgnoreCase(tipo)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
