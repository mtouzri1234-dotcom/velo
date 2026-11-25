package com.bicycle.api.repos;

import com.bicycle.api.entities.Bike;
import com.bicycle.api.entities.Category;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(path = "rest")
@CrossOrigin(origins = "http://localhost:4200")
public interface BikeRepository extends JpaRepository<Bike, Long> {
    List<Bike> findByName(String name);

    List<Bike> findByNameContaining(String name);

    List<Bike> findByNameContainingIgnoreCase(String name);

    @Query("select b from Bike b where lower(b.name) like lower(concat('%', :name, '%')) and b.price > :price")
    List<Bike> findByNameAndPrice(@Param("name") String name, @Param("price") java.math.BigDecimal price);

    @Query("select b from Bike b where b.category = :category")
    List<Bike> findByCategory(@Param("category") Category category);

    List<Bike> findByCategoryId(Long categoryId);

    List<Bike> findAllByOrderByNameAsc();

    List<Bike> findAllByOrderByNameAscPriceDesc();

    @Query("select b from Bike b order by b.name ASC, b.price DESC")
    List<Bike> sortByNameAscendingAndPriceDescending();
}