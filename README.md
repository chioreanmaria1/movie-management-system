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

### Screenings

- A screening can only be created in a hall that exists and is available.
- The movie of a screening must exist.
- Two screenings in the same hall cannot overlap in time. The end of a screening is its start plus the movie duration.

## Deletion Rules

The system uses four kinds of deletion, depending on how much the entity matters to the rest of the data. In every case, trying to delete an entity that does not exist throws an IllegalArgumentException.

1. **Soft Delete:**
   The record stays in the repository and its available flag is set to false. This is used for the physical infrastructure, because other records may still refer to it.
    - Entities: Theater, Hall, Seat
    - Only entities that exist and are currently available can be deleted. Otherwise an IllegalArgumentException is thrown.
    - An unavailable hall cannot be used for new screenings.
    - An unavailable seat cannot be booked.

2. **Hard delete with cascade:**
   The record is removed permanently, together with the dependent records that would otherwise point at nothing.
    - Entities: Screening

3. **Hard delete with restriction:**
   The record is removed permanently, but only if nothing depends on it. Otherwise the deletion is rejected with an IllegalArgumentException.
    - Entities: Movie, Customer, Staff

4. **Simple hard delete:**
   The record is removed with no side effects.
    - Entities: Ticket, StaffAssignments

### Known limitation

Deleting a screening does not remove or block its tickets, so tickets for a deleted screening remain in the system.

## Booking

When a ticket is saved, the checks run in this order. The first failing check throws an IllegalArgumentException.

1. The customer must exist.
2. The screening must exist.
3. The seat must exist and be available (soft-deleted seats cannot be booked).
4. The customer must be old enough to watch the movie being screened.

All lookups are validated before any of their values are used, so a missing customer or screening always produces a clear error message instead of a NullPointerException.

## Staff

- A staff member must have a name.
- A staff assignment requires an existing staff member and an existing screening.

## Running the Application

```bash
mvn spring-boot:run
```

The application starts on http://localhost:8080 by default.

## Running the Tests

```bash
mvn test
```
