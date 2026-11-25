package com.bicycle.api.restcontrollers;

import com.bicycle.api.entities.Category;
import com.bicycle.api.repos.CategoryRepository;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryRESTController {
    private final CategoryRepository categoryRepository;

    public CategoryRESTController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}