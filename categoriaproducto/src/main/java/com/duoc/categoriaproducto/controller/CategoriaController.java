package com.duoc.categoriaproducto.controller;

import com.duoc.categoriaproducto.dto.CategoriaDTO;
import com.duoc.categoriaproducto.dto.CategoriaRequest;
import com.duoc.categoriaproducto.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar(@RequestParam(required = false) String nombre) {
        log.info("Solicitud recibida para listar categorías. Filtro por nombre: '{}'", nombre);
        List<CategoriaDTO> categoria;
        if (nombre != null) {
            categoria = categoriaService.buscarPorNombre(nombre);
        } else {
            categoria = categoriaService.listar();
        }
        if (categoria.isEmpty()) {
            log.warn("No se encontraron categorías en la base de datos para la solicitud.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CategoriaDTO> guardar(@Valid @RequestBody CategoriaRequest request) {
        log.info("Solicitud para guardar una nueva categoría de producto: {}", request);
        return new ResponseEntity<>(categoriaService.guardar(request), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable int id){
        log.info("Iniciando búsqueda de categoría con ID: {}", id);
        try{
            return new ResponseEntity<>(categoriaService.buscarPorId(id), HttpStatus.OK);
        }
        catch (NullPointerException e){
            log.warn("No se encontró la categoría con ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> atualizar(@PathVariable int id, @Valid @RequestBody CategoriaRequest request) {
        log.info("Solicitud para actualizar categoría ID: {} con los datos: {}", id, request);
        try{
            return new ResponseEntity<>(categoriaService.actualizar(id, request), HttpStatus.OK);
        }
        catch (NullPointerException e) {
            log.warn("Falló la actualización: No existe la categoría con ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id){
        log.info("Procesando la eliminación definitiva de la categoría ID: {}", id);
        categoriaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
