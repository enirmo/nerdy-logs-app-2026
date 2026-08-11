# Nerdy Logs

Nerdy Logs is a Spring Boot web application that allows you to keep track of your media, including **games, movies, TV series, anime, and books**. Users can organize their personal library into different lists, while administrators can manage the public catalog and moderate users.

---

### Requirements
- Java 17
- Maven
- MySQL

---
## Technologies Used

### Main application
- Java 17
- Spring Boot 3.4
- Spring MVC
- Spring Security
- Spring Data JPA / Hibernate
- Thymeleaf
- OpenFeign
- Spring Cache
- Spring Scheduling
- MySQL
- Maven
- Lombok
- JUnit 5 / Mockito / MockMvc / JaCoCo

### Review microservice
- Java 17
- Spring Boot 4.1
- Spring Web / REST
- Spring Data JPA
- Spring Cache
- Spring Scheduling
- MySQL
- JUnit 5 / Mockito / MockMvc / JaCoCo

---

## Features

### User

- Register and sign in
- Browse and search the media catalog
- Filter media by type and genre
- Add catalog items to a personal library
- Organize entries as:
  - Watchlist
  - In Progress
  - Completed
  - Dropped
- Change an existing library entry's status (NEW)
- Remove entries from the library
- Search personal lists
- Add, edit, and delete reviews (NEW)
- View average ratings (NEW)
- Edit profile picture and biography
- Delete own account

### Administrator

- Add, edit, and delete catalog items
- Search users and catalog items
- Delete users
- Promote/demote users to ADMIN/USER (NEW)
- Prevent administrators from changing their own role (NEW)
- View searchable administrator action history

---

## Review Microservice

Reviews are handled by a separate Spring Boot REST service running on port 8081.

The main application communicates with it through OpenFeign.

Endpoints:

- `POST /api/reviews`
- `PUT /api/reviews`
- `DELETE /api/reviews/{reviewId}`
- `GET /api/reviews/user/{userId}/media/{mediaId}`
- `GET /api/reviews/media/{mediaId}/average`

If review retrieval temporarily fails, the main application degrades gracefully instead of making library pages unavailable.

## Security

- BCrypt password hashing
- Spring Security form login
- Session-based authentication
- CSRF protection
- USER and ADMIN roles
- `/admin/**` restricted to administrators

## Validation and Error Handling

Jakarta Bean Validation is used for server-side form validation.

Examples include:
- username length and required fields
- password minimum length
- valid email addresses
- item name/category/genre validation
- profile bio length
- profile-picture URL validation

Both applications contain global error handling for application exceptions.

## Caching

The main application caches catalog data.

- Catalog reads use `@Cacheable`
- Catalog changes invalidate the cache using `@CacheEvict`

The review microservice caches calculated average ratings.

- Average ratings are cached per media item
- Add/edit/delete review operations invalidate stale averages

## Scheduling

Two Spring scheduled tasks are used:

- The main application clears the catalog cache daily using a cron expression.
- The review microservice clears cached average ratings every two hours using a fixed-delay trigger.

## Testing

The project includes:
- Unit tests with JUnit 5 and Mockito
- API/web-layer tests with MockMvc
- Integration tests
- JaCoCo coverage reports

Current line coverage:
- Main application: 82%
- Review microservice: 100%

---

## Domain Model

### Main application
- User
- Item
- LibraryEntry
- AdminLog

### Review microservice
- Review

---

## Author

**Dobrena Pashova**

---

## Inspiration

This is my first larger Spring Boot project, created as part of a university exam.

The original inspiration came from a very real problem: my husband and I used to keep a huge list of movies, TV series and anime we wanted to watch together. Eventually we lost the list and started constantly asking ourselves, *"Didn't we already want to watch this?"*

Over time I realized the same thing happened with games. While platforms like Steam provide a library, they don't really help me keep track of what I planned to play next before inevitably getting distracted by something else.

That idea eventually grew into **Nerdy Logs**—a simple application where users can maintain their own watchlists, playlists and reading lists across multiple types of media.
