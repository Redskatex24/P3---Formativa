package com.duoc.pago.controller;

import com.duoc.pago.dto.PagoDTO;
import com.duoc.pago.dto.PagoRequest;
import com.duoc.pago.service.PagoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/pago")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoDTO>> listar(@RequestParam(required = false) String tipo) {
        log.info("Solicitud recibida para listar pagos. Filtro por tipo: '{}'", tipo);
        List<PagoDTO> pago;
        if (tipo != null) {
            pago = pagoService.buscarPorTipo(tipo);
        } else {
            pago = pagoService.listar();
        }
        if (pago.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        log.warn("No se encontraron registros de pagos para la solicitud realizada.");
        return new ResponseEntity<>(pago, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<PagoDTO> guardar(@Valid @RequestBody PagoRequest request) {
        log.info("Solicitud para procesar y guardar un nuevo pago: {}", request);
        return new ResponseEntity<>(pagoService.guardar(request), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> buscarPorId(@PathVariable int id){
        log.info("Iniciando búsqueda de pago con ID: {}", id);
        try{
            return new ResponseEntity<>(pagoService.buscarPorId(id), HttpStatus.OK);
        }
        catch (NullPointerException e){
            log.warn("No se encontró el registro de pago solicitado con ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> atualizar(@PathVariable int id, @Valid @RequestBody PagoRequest request) {
        log.info("Solicitud para actualizar pago ID: {} con datos: {}", id, request);
        try{
            return new ResponseEntity<>(pagoService.actualizar(id, request), HttpStatus.OK);
        }
        catch (NullPointerException e) {
            log.warn("Falló la actualización: No existe el registro de pago con ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id){
        log.info("Procesando la eliminación definitiva del registro de pago ID: {}", id);
        pagoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
