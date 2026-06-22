package com.duoc.categoriacliente.service;

import com.duoc.categoriacliente.dto.CategoriaDTO;
import com.duoc.categoriacliente.dto.CategoriaRequest;
import com.duoc.categoriacliente.exception.CategoriaNotFoundException;
import com.duoc.categoriacliente.model.CategoriaModel;
import com.duoc.categoriacliente.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaDTO guardar(CategoriaRequest request) {
        CategoriaModel categoria = new CategoriaModel();
        categoria.setNombre(request.getNombre());

        CategoriaModel guardado = categoriaRepository.save(categoria);
        return convertirADTO(guardado);
    }
    public List<CategoriaDTO> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    public CategoriaDTO buscarPorId(int id) {
        CategoriaModel clientes = categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id));
        return convertirADTO(clientes);
    }
    public CategoriaDTO actualizar(int id, CategoriaRequest request) {
        CategoriaModel categoriaExistente = categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id));
        categoriaExistente.setNombre(request.getNombre());

        CategoriaModel actualizado = categoriaRepository.save(categoriaExistente);
        return convertirADTO(actualizado);
    }

    public void eliminar(int id) {
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
        return categoriaRepository.findCategoriaModelByNombreContainsIgnoreCase(nombre)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
