package com.bicycle.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.bicycle.api.entities.Category;
import com.bicycle.api.repos.BikeRepository;
import com.bicycle.api.repos.CategoryRepository;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class BikeApiApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BikeRepository bikeRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	void categoriesAreLoadedFromTheDatabase() throws Exception {
		mockMvc.perform(get("/api/categories"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").isNumber())
				.andExpect(jsonPath("$[0].name").isString());
	}

	@Test
	void bikesCanBeSearchedByNameAndCategory() throws Exception {
		mockMvc.perform(get("/api/bikes/search").param("name", "trail"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].brand").value("Trek"));

		mockMvc.perform(get("/api/bikes/category/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].category.name").value("VTT"));
	}

	@Test
	void repositorySupportsWorkshopQueriesAndSorting() {
		assertEquals(1, bikeRepository.findByName("Trail 7").size());
		assertEquals(1, bikeRepository.findByNameContaining("Trail").size());
		assertEquals(1, bikeRepository.findByNameAndPrice("Trail", new BigDecimal("1000")).size());

		Category mountain = categoryRepository.findById(2L).orElseThrow();
		assertEquals(1, bikeRepository.findByCategory(mountain).size());
		assertEquals(1, bikeRepository.findByCategoryId(2L).size());
		assertEquals("Commuter E", bikeRepository.findAllByOrderByNameAsc().get(0).getName());
		assertEquals("Commuter E", bikeRepository.sortByNameAscendingAndPriceDescending().get(0).getName());
	}

	@Test
	void databaseUsersCanLoginWithExpectedRoles() throws Exception {
		mockMvc.perform(post("/api/auth/login")
					.contentType("application/json")
					.content("{\"username\":\"admin\",\"password\":\"123\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("admin"))
				.andExpect(jsonPath("$.roles[0]").value("ADMIN"));

		mockMvc.perform(post("/api/auth/login")
					.contentType("application/json")
					.content("{\"username\":\"youssef\",\"password\":\"123\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("youssef"))
				.andExpect(jsonPath("$.roles[0]").value("USER"));

		mockMvc.perform(post("/api/auth/login")
					.contentType("application/json")
					.content("{\"username\":\"admin\",\"password\":\"wrong\"}"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void springDataRestExposesIdsAndNameProjection() throws Exception {
		mockMvc.perform(get("/rest"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.bikes[0].id").isNumber());

		mockMvc.perform(get("/cat"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.categories[0].id").isNumber());

		mockMvc.perform(get("/rest?projection=nameOnly"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.bikes[0].name").isString());
	}

}
