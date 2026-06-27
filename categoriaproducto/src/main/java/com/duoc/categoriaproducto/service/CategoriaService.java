package com.duoc.categoriaproducto.service;

import com.duoc.categoriaproducto.dto.CategoriaDTO;
import com.duoc.categoriaproducto.dto.CategoriaRequest;
import com.duoc.categoriaproducto.exception.CategoriaNotFoundException;
import com.duoc.categoriaproducto.model.CategoriaModel;
import com.duoc.categoriaproducto.repository.CategoriaRepository;
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
        log.info("Iniciando el proceso para guardar una nueva categoría de producto: {}", request.getNombre());
        CategoriaModel categoria = new CategoriaModel();
        categoria.setNombre(request.getNombre());

        CategoriaModel guardado = categoriaRepository.save(categoria);
        log.info("Categoría de producto guardada con éxito. ID asignado: {}", guardado.getId());
        return convertirADTO(guardado);
    }
    public List<CategoriaDTO> listar() {
        log.info("Solicitando el listado completo de categorías desde la base de datos.");
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    public CategoriaDTO buscarPorId(int id) {
        log.info("Buscando categoría de producto por ID: {}", id);
        CategoriaModel clientes = categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id));
        return convertirADTO(clientes);
    }
    public CategoriaDTO actualizar(int id, CategoriaRequest request) {
        log.info("Iniciando actualización en categoría ID: {}. Nuevos datos: {}", id, request.getNombre());
        CategoriaModel categoriaExistente = categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id));
        categoriaExistente.setNombre(request.getNombre());

        CategoriaModel actualizado = categoriaRepository.save(categoriaExistente);
        log.info("Categoría ID: {} modificada con éxito en el sistema.", id);
        return convertirADTO(actualizado);
    }

    public void eliminar(int id) {
        log.info("Ejecutando la baja en base de datos para la categoría ID: {}", id);
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
        log.info("Filtrando categorías de productos que contengan en su nombre: '{}'", nombre);
        return categoriaRepository.findCategoriaModelByNombreContainsIgnoreCase(nombre)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
