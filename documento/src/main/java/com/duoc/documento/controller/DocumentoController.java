package com.duoc.documento.controller;

import com.duoc.documento.dto.DocumentoDTO;
import com.duoc.documento.dto.DocumentoRequest;
import com.duoc.documento.service.DocumentoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/documento")
public class DocumentoController {
    @Autowired
    private DocumentoService documentoService;

    @GetMapping
    public ResponseEntity<List<DocumentoDTO>> listar(@RequestParam(required = false) String tipo) {
        log.info("Solicitud recibida para listar documentos. Filtro por tipo: '{}'", tipo);
        List<DocumentoDTO> documento;
        if (tipo != null) {
            documento = documentoService.buscarPorTipo(tipo);
        } else {
            documento = documentoService.listar();
        }
        if (documento.isEmpty()) {
            log.warn("No se encontraron documentos en el sistema para la solicitud realizada.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(documento, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<DocumentoDTO> guardar(@Valid @RequestBody DocumentoRequest request) {
        log.info("Solicitud para registrar un nuevo documento: {}", request);
        return new ResponseEntity<>(documentoService.guardar(request), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDTO> buscarPorId(@PathVariable int id){
        log.info("Iniciando búsqueda de documento con ID: {}", id);
        try{
            return new ResponseEntity<>(documentoService.buscarPorId(id), HttpStatus.OK);
        }
        catch (NullPointerException e){
            log.warn("No se encontró el documento solicitado con ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<DocumentoDTO> atualizar(@PathVariable int id, @Valid @RequestBody DocumentoRequest request) {
        log.info("Solicitud para actualizar documento ID: {} con datos: {}", id, request);
        try{
            return new ResponseEntity<>(documentoService.actualizar(id, request), HttpStatus.OK);
        }
        catch (NullPointerException e) {
            log.warn("Falló la actualización: No existe el documento con ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id){
        log.info("Procesando la eliminación definitiva del documento ID: {}", id);
        documentoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}