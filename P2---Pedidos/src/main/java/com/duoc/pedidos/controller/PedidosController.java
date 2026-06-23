package com.duoc.pedidos.controller;

import com.duoc.pedidos.dto.PedidosDTO;
import com.duoc.pedidos.dto.PedidosRequest;
import com.duoc.pedidos.service.PedidosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidosController {
    @Autowired
    private PedidosService pedidosService;

    @GetMapping
    public ResponseEntity<List<PedidosDTO>> listar(@RequestParam(required = false) String pedido) {
        List<PedidosDTO> pedidos;
        if (pedido != null) {
            pedidos = pedidosService.buscarPorPedido(pedido);
        } else {
            pedidos = pedidosService.listar();
        }
        if (pedidos.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<PedidosDTO> guardar(@Valid @RequestBody PedidosRequest request) {
        return new ResponseEntity<>(pedidosService.guardar(request), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PedidosDTO> buscarPorId(@PathVariable int id){
        try{
            return new ResponseEntity<>(pedidosService.buscarPorId(id), HttpStatus.OK);}
        catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<PedidosDTO> atualizar(@PathVariable int id, @Valid @RequestBody PedidosRequest request) {
        try{
            return new ResponseEntity<>(pedidosService.actualizar(id, request), HttpStatus.OK);}
        catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id){
        pedidosService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
