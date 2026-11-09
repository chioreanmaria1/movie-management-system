# Movie Management System

Spring Boot Application, that handles the screenings of movies, the customers and the staff of cinemas.

- Java: Version 17
- Build Tool: Maven

## Project Structure and Architecture

The project has a Model-Repository-Service-Controller structure.

```
src/main/java/com/example/moviemanagement/
|
|-- model/        # UML Entities (Theatre, Hall, Seat, Movie, Screening, Customer, Ticket, Staff, etc.)
|-- repository/   # In-Memory data management using LinkedHashMap
|-- service/      # Business logic (CinemaService, CustomerService, StaffService, BookingService)
|-- controller/   # Web controllers for handling requests

src/main/resources/
|
|-- templates/    # Thymeleaf pages, one folder per entity (index.html and form.html)
|-- application.properties
```

Each entity has a repository that stores its objects in a LinkedHashMap, which gives O(1) lookup by id.
The interface BaseRepository contains the functionality common to all repositories.
The abstract class InMemoryRepository implements it and holds the map.
Each concrete repository extends InMemoryRepository and implements the abstract hasId method.

When objects are saved, the internal lists inside entities (for example the list of halls inside a theatre) stay empty. This avoids data redundancy and keeps the repositories as the single source of truth, while still matching the UML structure. Relations are therefore resolved through ids and repository lookups, not through these lists.

### Services

| Service | Responsibility |
|---|---|
| CinemaService | Theatres, halls, seats, movies and screenings |
| CustomerService | Customers and their tickets |
| BookingService | Ticket creation, lookup and deletion |
| StaffService | Staff and staff assignments |

Services validate foreign keys before saving. For example, a hall can only be saved if its theatreId refers to an existing theatre.

## Business Rules

### Availability

Theatres, halls and seats have an available flag that can be changed with `setTheatreAvailability`, `setHallAvailability` and `setSeatAvailability`. Trying to change the availability of an entity that does not exist throws an IllegalArgumentException.

- Changing a theatre changes all its halls and their seats, and changing a hall changes all its seats.
- An unavailable theatre cannot receive new halls.
- An unavailable hall cannot receive new seats or new screenings.
- An unavailable seat cannot be booked.

### Screenings

- A screening can only be created in a hall that exists and is available.
- The movie of a screening must exist.
- Two screenings in the same hall cannot overlap in time. The end of a screening is its start plus the movie duration. A screening can start exactly when another one ends.

### Customers

- A customer must have a name.

### Staff

- A staff member must have a name.
- Support staff must have a role, and technical operators must have a specialization.
- A staff member must be either SupportStaff or TechnicalOperator.
- The id of a staff member must be unique across both types.
- A staff assignment requires an existing staff member and an existing screening.

## Booking

When a ticket is saved, the checks run in this order. The first failing check throws an IllegalArgumentException.

1. The customer must exist.
2. The screening must exist.
3. The seat must exist and be available.
4. The customer must be old enough to watch the movie being screened.
5. The seat must not already be booked for the same screening.
6. The seat must belong to the hall of the screening.

All lookups are validated before any of their values are used, so a missing customer, screening or seat always produces a clear error message instead of a NullPointerException.

## Web Interface

The application has a simple server-rendered web interface built with Spring MVC and Thymeleaf. Each entity has its own controller and its own folder under `src/main/resources/templates/`, containing two pages:
- **`index.html`:** a list of all records, with a delete button for each. The controller puts the list of entities in the model (for movies, under the name `movies`). The template goes through it with `th:each` and builds one table row per record. Each row has a small form with a Delete button that posts to `/movies/{id}/delete`.
- **`form.html`:** a form for adding a new record. The controller puts an empty object in the model (`movie`). The `<form>` is bound to it with `th:object`, and each input is bound to a field with `th:field="*{fieldName}"`. When the form is submitted, Spring fills the object with the entered values and passes it to the controller, which calls the service to save it.

Special field types:

- **Enum fields:** shown as a `<select>`. The controller puts all the enum values in the model (for example `Genre.values()` under the name `genres`), and the template creates one `<option>` for each value.
- **Boolean fields:** shown as a checkbox (for example `ageRestricted`).

## Deletion Rules

The system uses three kinds of deletion, depending on how much the entity matters to the rest of the data. In every case, trying to delete an entity that does not exist throws an IllegalArgumentException.

1. **Hard delete with cascade:**
   The record is removed permanently, together with the dependent records that would otherwise point at nothing.
    - Entities: Theatre, Hall, Staff

2. **Hard delete with restriction:**
   The record is removed permanently, but only if nothing depends on it. Otherwise, the deletion is rejected with an IllegalArgumentException.
    - Entities: Movie, Customer, Screening

3. **Simple hard delete:**
   The record is removed with no side effects.
    - Entities: Seat, Ticket, StaffAssignment

### Known limitations
- Deleting a theatre or a hall does not check for screenings, so screenings may remain that refer to a hall that no longer exists.
- Deleting a seat does not check for tickets, so tickets may remain that refer to a seat that no longer exists.
- Making a hall or a seat available again does not check whether its parent (theatre or hall) is available.

## Running the Application

```bash
mvn spring-boot:run
```

The application starts on http://localhost:8080 by default.

## Running the Tests

```bash
mvn test
```