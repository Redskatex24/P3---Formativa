package com.duoc.venta.controller;

import com.duoc.venta.dto.VentaDTO;
import com.duoc.venta.dto.VentaRequest;
import com.duoc.venta.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> listar(@RequestParam(required = false) String venta) {
        List<VentaDTO> ventas;
        if (venta != null) {
            ventas = ventaService.buscarPorVenta(venta);
        } else {
            ventas = ventaService.listar();
        }
        if (ventas.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<VentaDTO> guardar(@Valid @RequestBody VentaRequest request) {
        return new ResponseEntity<>(ventaService.guardar(request), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> buscarPorId(@PathVariable int id){
        try{
            return new ResponseEntity<>(ventaService.buscarPorId(id), HttpStatus.OK);}
        catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> atualizar(@PathVariable int id, @Valid @RequestBody VentaRequest request) {
        try{
            return new ResponseEntity<>(ventaService.actualizar(id, request), HttpStatus.OK);}
        catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id){
        ventaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}