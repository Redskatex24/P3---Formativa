package com.duoc.pago.service;

import com.duoc.pago.dto.PagoDTO;
import com.duoc.pago.dto.PagoRequest;
import com.duoc.pago.exception.PagoNotFoundException;
import com.duoc.pago.model.PagoModel;
import com.duoc.pago.repository.PagoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    public PagoDTO guardar(PagoRequest request) {
        log.info("Iniciando el proceso para registrar un nuevo pago de tipo: {}", request.getTipo());
        PagoModel pago = new PagoModel();
        pago.setTipo(request.getTipo());

        PagoModel guardado = pagoRepository.save(pago);
        log.info("Pago registrado exitosamente con ID: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public List<PagoDTO> listar() {
        log.info("Solicitando el listado completo de pagos desde la base de datos.");
        return pagoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public PagoDTO buscarPorId(int id) {
        log.info("Buscando registro de pago por ID: {}", id);
        PagoModel pago = pagoRepository.findById(id).orElseThrow(() -> new PagoNotFoundException(id));
        return convertirADTO(pago);
    }

    public PagoDTO actualizar(int id, PagoRequest request) {
        log.info("Iniciando actualización en el pago ID: {}. Nuevo tipo asignado: {}", id, request.getTipo());
        PagoModel pagoExistente = pagoRepository.findById(id).orElseThrow(() -> new PagoNotFoundException(id));
        pagoExistente.setTipo(request.getTipo());

        PagoModel actualizado = pagoRepository.save(pagoExistente);
        log.info("Registro de pago ID: {} modificado con éxito en el sistema.", id);
        return convertirADTO(actualizado);
    }

    public void eliminar(int id) {
        log.info("Ejecutando la eliminación en la base de datos para el pago ID: {}", id);
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
        log.info("Filtrando registros de pagos que contengan el tipo: '{}'", tipo);
        return pagoRepository.findPagoModelByTipoContainsIgnoreCase(tipo)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
