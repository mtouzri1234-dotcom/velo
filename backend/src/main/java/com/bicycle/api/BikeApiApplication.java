package com.bicycle.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import com.bicycle.api.entities.Bike;
import com.bicycle.api.entities.Category;
import com.bicycle.api.repos.BikeRepository;
import com.bicycle.api.repos.CategoryRepository;
import com.bicycle.api.repos.UserRepository;
import com.bicycle.api.entities.AppUser;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BikeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BikeApiApplication.class, args);
	}

	@Bean
	CommandLineRunner seedDatabase(CategoryRepository categories, BikeRepository bikes,
			RepositoryRestConfiguration repositoryRestConfiguration, UserRepository users) {
		return args -> {
			repositoryRestConfiguration.exposeIdsFor(Bike.class, Category.class, AppUser.class);
			if (users.count() == 0) {
				users.save(new AppUser("admin", "123", "ADMIN"));
				users.save(new AppUser("youssef", "123", "USER"));
			}
			if (categories.count() > 0) {
				return;
			}

			Category city = new Category("Ville", "Déplacements urbains et trajets quotidiens");
			Category mountain = new Category("VTT", "Sentiers, chemins et terrains techniques");
			Category road = new Category("Route", "Sorties sportives et longues distances");
			Category electric = new Category("Électrique", "Assistance électrique pour aller plus loin");
			List<Category> seededCategories = new ArrayList<>();
			seededCategories.add(city);
			seededCategories.add(mountain);
			seededCategories.add(road);
			seededCategories.add(electric);
			categories.saveAll(seededCategories);

			List<Bike> seededBikes = new ArrayList<>();
			seededBikes.add(bike("Metro 2", "Gazelle", "M", "Ville", "749.00", 8, false, city));
			seededBikes.add(bike("Trail 7", "Trek", "L", "VTT", "1299.00", 5, false, mountain));
			seededBikes.add(bike("Endurace 6", "Canyon", "M", "Route", "1849.00", 3, false, road));
			seededBikes.add(bike("Powerfly 4", "Trek", "L", "VTT", "3299.00", 2, true, electric));
			seededBikes.add(bike("Commuter E", "Gazelle", "M", "Ville", "2499.00", 6, true, electric));
			bikes.saveAll(seededBikes);
		};
	}

	private Bike bike(String name, String brand, String frameSize, String bicycleType,
			String price, int stock, boolean electric, Category category) {
		Bike bike = new Bike();
		bike.setName(name);
		bike.setBrand(brand);
		bike.setFrameSize(frameSize);
		bike.setBicycleType(bicycleType);
		bike.setPrice(new BigDecimal(price));
		bike.setStock(stock);
		bike.setElectric(electric);
		bike.setCategory(category);
		return bike;
	}

}
