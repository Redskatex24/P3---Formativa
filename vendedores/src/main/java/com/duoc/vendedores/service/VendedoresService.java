package com.duoc.vendedores.service;

import com.duoc.vendedores.dto.VendedoresDTO;
import com.duoc.vendedores.dto.VendedoresRequest;
import com.duoc.vendedores.exception.VendedorNotFoundException;
import com.duoc.vendedores.model.VendedoresModel;
import com.duoc.vendedores.repository.VendedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendedoresService {
    @Autowired
    private VendedoresRepository vendedoresRepository;

    public VendedoresDTO guardar(VendedoresRequest request) {
        VendedoresModel vendedores = new VendedoresModel();
        vendedores.setNombre(request.getNombre());

        return convertirADTO(vendedoresRepository.save(vendedores));
    }
    public List<VendedoresDTO> listar() {
        return vendedoresRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    public VendedoresDTO buscarPorId(int id) {
        VendedoresModel vendedores = vendedoresRepository.findById(id).orElseThrow(() -> new VendedorNotFoundException(id));
        return convertirADTO(vendedores);
    }
    public VendedoresDTO actualizar(int id, VendedoresRequest request) {
        VendedoresModel vendedorExistente = vendedoresRepository.findById(id).orElseThrow(() -> new VendedorNotFoundException(id));
        vendedorExistente.setNombre(request.getNombre());

        return convertirADTO(vendedoresRepository.save(vendedorExistente));
    }

    public void eliminar(int id) {
        vendedoresRepository.findById(id).orElseThrow(() -> new VendedorNotFoundException(id));
        vendedoresRepository.deleteById(id);
    }
    public List<VendedoresDTO> buscarPorNombre(String nombre) {
        return vendedoresRepository.findVendedoresModelByNombreContainsIgnoreCase(nombre)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private VendedoresDTO convertirADTO(VendedoresModel vendedores) {
        if(vendedores == null) return null;
        VendedoresDTO dto = new VendedoresDTO();
        dto.setId(vendedores.getId());
        dto.setNombre(vendedores.getNombre());
        return dto;
    }
}
