package com.duoc.categoriacliente.service;

import com.duoc.categoriacliente.dto.CategoriaDTO;
import com.duoc.categoriacliente.dto.CategoriaRequest;
import com.duoc.categoriacliente.exception.CategoriaNotFoundException;
import com.duoc.categoriacliente.model.CategoriaModel;
import com.duoc.categoriacliente.repository.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaDTO guardar(CategoriaRequest request) {
        log.info("Iniciando persistencia de nueva categoría: {}", request.getNombre());
        CategoriaModel categoria = new CategoriaModel();
        categoria.setNombre(request.getNombre());

        CategoriaModel guardado = categoriaRepository.save(categoria);
        log.info("Categoría guardada exitosamente con ID: {}", guardado.getId());
        return convertirADTO(guardado);
    }
    public List<CategoriaDTO> listar() {
        log.info("Ejecutando consulta de todas las categorías en la base de datos.");
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    public CategoriaDTO buscarPorId(int id) {
        log.info("Solicitando lectura de categoría ID: {}", id);
        CategoriaModel clientes = categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id));
        return convertirADTO(clientes);
    }
    public CategoriaDTO actualizar(int id, CategoriaRequest request) {
        log.info("Iniciando actualización de categoría ID: {} con nuevos datos: {}", id, request.getNombre());
        CategoriaModel categoriaExistente = categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id));
        categoriaExistente.setNombre(request.getNombre());
        CategoriaModel actualizado = categoriaRepository.save(categoriaExistente);
        log.info("Categoría ID: {} actualizada con éxito en la base de datos.", id);
        return convertirADTO(actualizado);
    }

    public void eliminar(int id) {
        log.info("Eliminando de la base de datos la categoría con ID: {}", id);
        categoriaRepository.deleteById(id);
    }

    private CategoriaDTO convertirADTO(CategoriaModel clientes) {
        if(clientes == null) return null;
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(clientes.getId());
        dto.setNombre(clientes.getNombre());
        return dto;
    }
    public List<CategoriaDTO> buscarPorNombre(String nombre) {
        log.info("Buscando categorías que coincidan con el término: '{}'", nombre);
        return categoriaRepository.findCategoriaModelByNombreContainsIgnoreCase(nombre)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
