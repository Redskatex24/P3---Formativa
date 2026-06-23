package com.duoc.vendedores.controller;

import com.duoc.vendedores.dto.VendedoresDTO;
import com.duoc.vendedores.dto.VendedoresRequest;
import com.duoc.vendedores.service.VendedoresService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendedores")
public class VendedoresController {
    @Autowired
    private VendedoresService vendedoresService;

    @GetMapping
    public ResponseEntity<List<VendedoresDTO>> listar(@RequestParam(required = false) String nombre){
        List<VendedoresDTO> vendedores;
        if (nombre != null) {
            vendedores = vendedoresService.buscarPorNombre(nombre);
        } else {
            vendedores = vendedoresService.listar();
        }
        if (vendedores.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(vendedores, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<VendedoresDTO> guardar(@Valid @RequestBody VendedoresRequest request) {
        return new ResponseEntity<>(vendedoresService.guardar(request), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VendedoresDTO> buscarPorId(@PathVariable Integer id) {
        return new ResponseEntity<>(vendedoresService.buscarPorId(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<VendedoresDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody VendedoresRequest request) {
        return new ResponseEntity<>(vendedoresService.actualizar(id, request), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        vendedoresService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
