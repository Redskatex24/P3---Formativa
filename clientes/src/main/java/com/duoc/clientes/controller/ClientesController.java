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
        List<ClientesDTO> clientes;
        if (nombre != null) {
            clientes = clientesService.buscarPorNombre(nombre);
        } else {
            clientes = clientesService.listar();
        }
        if (clientes.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ClientesDTO> guardar(@Valid @RequestBody ClientesRequest request) {
        log.info("El request para crear un cliente fue: " + request);
        return new ResponseEntity<>(clientesService.guardar(request), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientesDTO> buscarPorId(@PathVariable int id){
        try{
            return new ResponseEntity<>(clientesService.buscarPorId(id), HttpStatus.OK);}
        catch (NullPointerException e){
            log.error("No se ha encontrado el id de cliente: " + id + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClientesDTO> atualizar(@PathVariable int id, @Valid @RequestBody ClientesRequest request) {
        try{
            return new ResponseEntity<>(clientesService.actualizar(id, request), HttpStatus.OK);}
        catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id){
        clientesService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
