package com.example.currencyexchanger.repositories;
import com.example.currencyexchanger.model.ExchangeRate;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(Long id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
}
