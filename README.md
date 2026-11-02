Movie Management System

Spring Boot Application, that handles the screenings of movies, the customers and the staff of cinemas.

Java: Version 17
Build Tool: Maven

Project Structure and Architecture
The project has a Modell-Repository-Service-Controller structure.

src/main/java/com/example/moviemanagement/
│
├── model/           # UML Entities (Theatre, Hall, Seat, Movie, Screening, Customer, Ticket, Staff, etc.)
├── repository/      # In-Memory data management using LinkedHashMap
├── service/         # Business logic (CinemaService, CustomerService, StaffService, BookingService)
└── controller/      # Web controllers for handling requests

In-Memory Repository:
Each entity has a repository that stores all its objects in a LinkedHashMap to ensure O(1) searching by id.
We use an interface BaseRepository that contains all common functionalities of the repositories and the abstract class InMemoryRepository that implements it,
which contains the map; each repository will extend this class and implement the interface hasId;

Single Source of Truth: When objects are saved, internal lists within entities (like lists of halls inside a theatre) remain empty to avoid
data redundancy and maintain a clean single source of truth, while still fulfilling the UML structural requirements.

Service:
We split the business logic into services: 
-CinemaService for handling theatres, seats, movies and screenings
-CustomerService for handling Customers and their tickets
//Booking
//StaffService

Services validate foreign keys before saving (for example you save a hall only if its theaterId exists).
Deletion Rules:
-Composition: Deletion is blocked if dependent child objects exist (you cannot delete a theatre that still contains halls, or a customer with active tickets).
-Aggregation: Deleting a parent automatically cleans up dependent entities (deleting a screening cascades and removes associated staff assignments).