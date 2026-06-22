package com.duoc.pago.service;

import com.duoc.pago.dto.PagoDTO;
import com.duoc.pago.dto.PagoRequest;
import com.duoc.pago.exception.PagoNotFoundException;
import com.duoc.pago.model.PagoModel;
import com.duoc.pago.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    public PagoDTO guardar(PagoRequest request) {
        PagoModel pago = new PagoModel();
        pago.setTipo(request.getTipo());

        PagoModel guardado = pagoRepository.save(pago);
        return convertirADTO(guardado);
    }

    public List<PagoDTO> listar() {
        return pagoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public PagoDTO buscarPorId(int id) {
        PagoModel pago = pagoRepository.findById(id).orElseThrow(() -> new PagoNotFoundException(id));
        return convertirADTO(pago);
    }

    public PagoDTO actualizar(int id, PagoRequest request) {
        PagoModel pagoExistente = pagoRepository.findById(id).orElseThrow(() -> new PagoNotFoundException(id));
        pagoExistente.setTipo(request.getTipo());

        PagoModel actualizado = pagoRepository.save(pagoExistente);
        return convertirADTO(actualizado);
    }

    public void eliminar(int id) {
        pagoRepository.deleteById(id);
    }

    private PagoDTO convertirADTO(PagoModel pago) {
        if (pago == null) return null;
        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        dto.setTipo(pago.getTipo());
        return dto;
    }
    public List<PagoDTO> buscarPorTipo(String tipo) {
        return pagoRepository.findPagoModelByTipoContainsIgnoreCase(tipo)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
