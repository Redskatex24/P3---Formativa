package com.duoc.clientes.controller;

import com.duoc.clientes.dto.ClientesDTO;
import com.duoc.clientes.dto.ClientesRequest;
import com.duoc.clientes.service.ClientesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Clientes", description = "Operaciones relacionadas con la gestión de clientes")
@RestController
@RequestMapping("/api/v1/clientes")
public class ClientesController {
    @Autowired
    private ClientesService clientesService;

    @GetMapping
    public ResponseEntity<List<ClientesDTO>> listar(@RequestParam(required = false) String nombre) {
        log.info("Solicitud recibida para listar clientes. Filtro por nombre: '{}'", nombre);
        List<ClientesDTO> clientes;
        if (nombre != null) {
            clientes = clientesService.buscarPorNombre(nombre);
        } else {
            clientes = clientesService.listar();
        }
        if (clientes.isEmpty()) {
            log.warn("No se encontraron clientes para la búsqueda realizada.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ClientesDTO> guardar(@Valid @RequestBody ClientesRequest request) {
        log.info("Solicitud para guardar un nuevo cliente: {}", request);
        return new ResponseEntity<>(clientesService.guardar(request), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientesDTO> buscarPorId(@PathVariable int id){
        log.info("Iniciando búsqueda de cliente con ID: {}", id);
        try{
            return new ResponseEntity<>(clientesService.buscarPorId(id), HttpStatus.OK);}
        catch (NullPointerException e){
            log.warn("No se encontró el cliente con ID: {}. Detalles: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClientesDTO> atualizar(@PathVariable int id, @Valid @RequestBody ClientesRequest request) {
        log.info("Solicitud para actualizar cliente ID: {} con los datos: {}", id, request);
        try{
            return new ResponseEntity<>(clientesService.actualizar(id, request), HttpStatus.OK);}
        catch (NullPointerException e) {
            log.warn("Falló la actualización: No existe el cliente con ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id){
        log.info("Procesando la eliminación del cliente ID: {}", id);
        clientesService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
