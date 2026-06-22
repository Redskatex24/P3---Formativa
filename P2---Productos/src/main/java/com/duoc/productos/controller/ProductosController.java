package com.duoc.productos.controller;

import com.duoc.productos.dto.ProductosDTO;
import com.duoc.productos.dto.ProductosRequest;
import com.duoc.productos.service.ProductosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductosController {
    @Autowired
    private ProductosService productosService;

    @GetMapping
    public ResponseEntity<List<ProductosDTO>> listar(@RequestParam(required = false) String nombre) {
        List<ProductosDTO> productos;
        if (nombre != null) {
            productos = productosService.buscarPorNombre(nombre);
        } else {
            productos = productosService.listar();
        }
        if (productos.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ProductosDTO> guardar(@Valid @RequestBody ProductosRequest request) {
        return new ResponseEntity<>(productosService.guardar(request), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductosDTO> buscarPorId(@PathVariable int id){
        try{
            return new ResponseEntity<>(productosService.buscarPorId(id), HttpStatus.OK);}
        catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductosDTO> atualizar(@PathVariable int id, @Valid @RequestBody ProductosRequest request) {
        try{
            return new ResponseEntity<>(productosService.actualizar(id, request), HttpStatus.OK);}
        catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id){
        productosService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
