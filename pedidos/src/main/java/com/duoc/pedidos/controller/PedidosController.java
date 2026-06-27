package com.duoc.pedidos.controller;

import com.duoc.pedidos.dto.PedidosDTO;
import com.duoc.pedidos.dto.PedidosRequest;
import com.duoc.pedidos.service.PedidosService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidosController {
    @Autowired
    private PedidosService pedidosService;

    @GetMapping
    public ResponseEntity<List<PedidosDTO>> listar(@RequestParam(required = false) String pedido) {
        log.info("Solicitud recibida para listar pedidos. Filtro de búsqueda: '{}'", pedido);
        List<PedidosDTO> pedidos;
        if (pedido != null) {
            pedidos = pedidosService.buscarPorPedido(pedido);
        } else {
            pedidos = pedidosService.listar();
        }
        if (pedidos.isEmpty()) {
            log.warn("No se encontraron registros de pedidos para la solicitud realizada.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<PedidosDTO> guardar(@Valid @RequestBody PedidosRequest request) {
        log.info("Solicitud para procesar y guardar un nuevo pedido: {}", request);
        return new ResponseEntity<>(pedidosService.guardar(request), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PedidosDTO> buscarPorId(@PathVariable int id){
        log.info("Iniciando búsqueda de pedido con ID: {}", id);
        try{
            return new ResponseEntity<>(pedidosService.buscarPorId(id), HttpStatus.OK);}
        catch (NullPointerException e){
            log.warn("No se encontró el registro de pedido solicitado con ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<PedidosDTO> atualizar(@PathVariable int id, @Valid @RequestBody PedidosRequest request) {
        log.info("Solicitud para actualizar pedido ID: {} con datos: {}", id, request);
        try{
            return new ResponseEntity<>(pedidosService.actualizar(id, request), HttpStatus.OK);}
        catch (NullPointerException e) {
            log.warn("Falló la actualización: No existe el registro de pedido con ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id){
        log.info("Procesando la eliminación definitiva del registro de pedido ID: {}", id);
        pedidosService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
