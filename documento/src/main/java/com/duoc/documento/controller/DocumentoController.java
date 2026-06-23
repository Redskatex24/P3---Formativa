package com.duoc.documento.controller;

import com.duoc.documento.dto.DocumentoDTO;
import com.duoc.documento.dto.DocumentoRequest;
import com.duoc.documento.service.DocumentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documento")
public class DocumentoController {
    @Autowired
    private DocumentoService documentoService;

    @GetMapping
    public ResponseEntity<List<DocumentoDTO>> listar(@RequestParam(required = false) String tipo) {
        List<DocumentoDTO> documento;
        if (tipo != null) {
            documento = documentoService.buscarPorTipo(tipo);
        } else {
            documento = documentoService.listar();
        }
        if (documento.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(documento, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<DocumentoDTO> guardar(@Valid @RequestBody DocumentoRequest request) {
        return new ResponseEntity<>(documentoService.guardar(request), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDTO> buscarPorId(@PathVariable int id){
        try{
            return new ResponseEntity<>(documentoService.buscarPorId(id), HttpStatus.OK);
        }
        catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<DocumentoDTO> atualizar(@PathVariable int id, @Valid @RequestBody DocumentoRequest request) {
        try{
            return new ResponseEntity<>(documentoService.actualizar(id, request), HttpStatus.OK);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id){
        documentoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}