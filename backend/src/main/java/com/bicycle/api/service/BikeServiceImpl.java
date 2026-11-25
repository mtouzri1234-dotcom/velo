package com.bicycle.api.service;

import com.bicycle.api.entities.Bike;
import com.bicycle.api.entities.Category;
import com.bicycle.api.repos.BikeRepository;
import com.bicycle.api.repos.CategoryRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class BikeServiceImpl implements BikeService {
    private final BikeRepository bikeRepository;
    private final CategoryRepository categoryRepository;

    public BikeServiceImpl(BikeRepository bikeRepository, CategoryRepository categoryRepository) {
        this.bikeRepository = bikeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Bike> findAll() {
        return bikeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Bike findById(Long id) {
        long validId = requireId(id);
        return bikeRepository.findById(validId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vélo introuvable"));
    }

    @Transactional(readOnly = true)
    public List<Bike> searchByName(String name) {
        return bikeRepository.findByNameContainingIgnoreCase(name.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bike> findByName(String name) {
        return bikeRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bike> findByNameContaining(String name) {
        return bikeRepository.findByNameContaining(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bike> findByNameAndPrice(String name, BigDecimal price) {
        return bikeRepository.findByNameAndPrice(name, price);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bike> findByCategory(Category category) {
        return bikeRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Bike> findByCategory(Long categoryId) {
        return bikeRepository.findByCategoryId(categoryId);
    }

    @Transactional(readOnly = true)
    public List<Bike> findSorted() {
        return bikeRepository.sortByNameAscendingAndPriceDescending();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bike> findByNameAscending() {
        return bikeRepository.findAllByOrderByNameAsc();
    }

    public Bike create(Bike bike) {
        bike.setId(null);
        bike.setCategory(findCategory(bike.getCategory().getId()));
        return bikeRepository.save(bike);
    }

    public Bike update(Long id, Bike changes) {
        Bike bike = findById(id);
        bike.setName(changes.getName());
        bike.setBrand(changes.getBrand());
        bike.setFrameSize(changes.getFrameSize());
        bike.setBicycleType(changes.getBicycleType());
        bike.setPrice(changes.getPrice());
        bike.setStock(changes.getStock());
        bike.setElectric(changes.isElectric());
        bike.setCategory(findCategory(changes.getCategory().getId()));
        return bikeRepository.save(bike);
    }

    public void delete(Long id) {
        long validId = requireId(id);
        if (!bikeRepository.existsById(validId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vélo introuvable");
        }
        bikeRepository.deleteById(validId);
    }

    private Category findCategory(Long id) {
        long validId = requireId(id);
        return categoryRepository.findById(validId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Catégorie introuvable"));
    }

    private long requireId(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Identifiant obligatoire");
        }
        return id.longValue();
    }
}