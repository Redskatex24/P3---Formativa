package com.duoc.stocks.repository;

import com.duoc.stocks.model.StocksModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StocksRepository extends JpaRepository<StocksModel, Integer> {
    List<StocksModel> findStocksModelById(int id);
}
