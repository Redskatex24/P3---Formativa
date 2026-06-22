package com.duoc.stocks.service;

import com.duoc.stocks.dto.StocksDTO;
import com.duoc.stocks.dto.StocksRequest;
import com.duoc.stocks.exception.StocksNotFoundException;
import com.duoc.stocks.model.StocksModel;
import com.duoc.stocks.repository.StocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StocksService {
    @Autowired
    private StocksRepository stocksRepository;

    public StocksDTO guardar(StocksRequest request) {
        StocksModel stocks = new StocksModel();
        stocks.setCantidad(request.getCantidad());

        StocksModel guardado = stocksRepository.save(stocks);
        return convertirADTO(guardado);
    }
    public List<StocksDTO> listar() {
        return stocksRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    public StocksDTO buscarPorId(int id) {
        StocksModel stock = stocksRepository.findById(id).orElseThrow(() -> new StocksNotFoundException(id));
        StocksDTO stockDTO = new StocksDTO();
        stockDTO.setCantidad(stock.getCantidad());
        return convertirADTO(stock);
    }
    public StocksDTO actualizar(int id, StocksRequest request) {
        StocksModel stockExistente  = stocksRepository.findById(id).orElseThrow(() -> new StocksNotFoundException(id));
        stockExistente.setCantidad(request.getCantidad());

        StocksModel actualizado = stocksRepository.save(stockExistente);
        return convertirADTO(actualizado);
    }
    public void eliminar(int id) {
        stocksRepository.deleteById(id);
    }
    private StocksDTO convertirADTO(StocksModel stocks) {
        if(stocks == null) return null;
        StocksDTO dto = new StocksDTO();
        dto.setId(stocks.getId());
        dto.setCantidad(stocks.getCantidad());
        return dto;
    }
}
