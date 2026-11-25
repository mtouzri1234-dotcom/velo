package com.bicycle.api.service;

import com.bicycle.api.entities.Bike;
import com.bicycle.api.entities.Category;
import java.math.BigDecimal;
import java.util.List;

public interface BikeService {
    List<Bike> findAll();
    Bike findById(Long id);
    List<Bike> searchByName(String name);
    List<Bike> findByName(String name);
    List<Bike> findByNameContaining(String name);
    List<Bike> findByNameAndPrice(String name, BigDecimal price);
    List<Bike> findByCategory(Category category);
    List<Bike> findByCategory(Long categoryId);
    List<Bike> findSorted();
    List<Bike> findByNameAscending();
    Bike create(Bike bike);
    Bike update(Long id, Bike bike);
    void delete(Long id);
}