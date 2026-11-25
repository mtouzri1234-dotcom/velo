package com.bicycle.api.restcontrollers;

import com.bicycle.api.entities.Bike;
import com.bicycle.api.service.BikeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bikes")
@CrossOrigin(origins = "http://localhost:4200")
public class BikeRESTController {
    private final BikeService bikeService;

    public BikeRESTController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping
    public List<Bike> findAll() {
        return bikeService.findAll();
    }

    @GetMapping("/sorted")
    public List<Bike> findSorted() {
        return bikeService.findSorted();
    }

    @GetMapping("/search")
    public List<Bike> search(@RequestParam String name) {
        return bikeService.searchByName(name);
    }

    @GetMapping("/category/{categoryId}")
    public List<Bike> findByCategory(@PathVariable Long categoryId) {
        return bikeService.findByCategory(categoryId);
    }

    @GetMapping("/{id}")
    public Bike findById(@PathVariable Long id) {
        return bikeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bike create(@Valid @RequestBody Bike bike) {
        return bikeService.create(bike);
    }

    @PutMapping("/{id}")
    public Bike update(@PathVariable Long id, @Valid @RequestBody Bike bike) {
        return bikeService.update(id, bike);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bikeService.delete(id);
    }
}