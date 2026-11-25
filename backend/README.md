# Bicycle API

Spring Boot Maven project imported into Spring Tool Suite from this directory.

## Layers

- `src/main/java/com/bicycle/api/entities`: Bike and Category JPA entities
- `src/main/java/com/bicycle/api/repos`: Spring Data repositories
- `src/main/java/com/bicycle/api/service`: BikeService and BikeServiceImpl
- `src/main/java/com/bicycle/api/restcontrollers`: REST endpoints
- `src/main/resources/application.properties`: H2 persistence and server settings
- `src/test/java`: integration tests

## Run

From this directory:

```powershell
.\mvnw.cmd spring-boot:run
```

The API uses port 8081 and context path `/bikes-api`. Its persistent H2 database is `data/bicycle-db.mv.db`; the application seeds sample bike and category records only when the database has no categories.

## Spring Data REST

- `GET /bikes-api/rest` lists bikes and exposes IDs.
- `GET /bikes-api/cat` lists categories and exposes IDs.
- `GET /bikes-api/rest/search/findByNameContaining?name=Trail` searches bike names.
- `GET /bikes-api/rest/search/findByCategoryId?categoryId=2` filters by category.
- `GET /bikes-api/rest?projection=nameOnly` returns the bike-name projection.

## Test

Tests use an isolated in-memory H2 database and do not modify the persistent database:

```powershell
.\mvnw.cmd test
```
