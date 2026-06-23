package com.duoc.stocks.controller;

import com.duoc.stocks.dto.StocksDTO;
import com.duoc.stocks.dto.StocksRequest;
import com.duoc.stocks.service.StocksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
public class StocksController {
    @Autowired
    private StocksService stocksService;

    @GetMapping
    public ResponseEntity<List<StocksDTO>> listar() {
        List<StocksDTO> stocks = stocksService.listar();
        if (stocks.size() == 0) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<StocksDTO> guardar(@Valid @RequestBody StocksRequest request) {
        return new ResponseEntity<>(stocksService.guardar(request), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StocksDTO> buscarPorId(@PathVariable int id){
        try{
            return new ResponseEntity<>(stocksService.buscarPorId(id), HttpStatus.OK);}
        catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<StocksDTO> atualizar(@PathVariable int id, @Valid @RequestBody StocksRequest request) {
        try{
            return new ResponseEntity<>(stocksService.actualizar(id, request), HttpStatus.OK);}
        catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id){
        stocksService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
