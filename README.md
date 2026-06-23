# Nerdy Logs

Nerdy Logs is a Spring Boot web application that allows you to keep track of your media, including **games, movies, TV series, anime, and books**. Users can organize their personal library into different lists, while administrators can manage the public catalog and moderate users.

---

## Technologies Used

- Java 17
- Spring Boot 3.4
- Spring MVC
- Spring Data JPA (Hibernate)
- Thymeleaf
- MySQL
- Maven
- Lombok

---

## Features

### Guest

- Register a new account
- Log into an existing account
- Browse the public media catalog
- Search media by title
- Filter media by genre

### User

- Add media to a personal library
- Organize media into:
  - Watchlist
  - In Progress
  - Completed
  - Dropped
- Remove media from the personal library
- Search within personal lists
- Edit personal profile
  - Profile picture
  - Biography
- Delete own account

### Administrator

- Add new media items
- Delete media items
- Search the media catalog
- Search registered users
- Delete users
- View the administrator changelog

---

## Domain Model

The application consists of the following entities:

### User

Stores account information, profile data, and the user's role.

### Item

Represents a media item in the public catalog.

### LibraryEntry

Represents a user's relationship with a media item, including its current status.

### AdminLog

Stores administrative actions such as adding or deleting users and catalog items.

---

## Entity Relationships

- One **User** can have many **LibraryEntry** records.
- One **Item** can appear in many **LibraryEntry** records.
- Each **LibraryEntry** belongs to exactly one **User** and one **Item**.

---

## Authentication

- Passwords are securely hashed using BCrypt.
- Authentication is session-based.
- Administrator pages are accessible only to users with the **ADMIN** role.

---

## Validation

The application uses **Jakarta Bean Validation** to validate user input.

Examples include:

- Required fields
- Valid media category selection
- Required usernames and passwords

Validation errors are displayed directly in the corresponding forms.

---

## Project Structure

```text
src
├── controller
├── model
│   ├── entity
│   └── dto
├── repository
├── service
├── config
├── templates
└── static
```

---

## Running the Project

### Requirements

- Java 17+
- Maven
- MySQL

---

## Author

**Dobrena Pashova**

---

## Inspiration

This is my first larger Spring Boot project, created as part of a university exam.

The original inspiration came from a very real problem: my husband and I used to keep a huge list of movies, TV series and anime we wanted to watch together. Eventually we lost the list and started constantly asking ourselves, *"Didn't we already want to watch this?"*

Over time I realized the same thing happened with games. While platforms like Steam provide a library, they don't really help me keep track of what I planned to play next before inevitably getting distracted by something else.

That idea eventually grew into **Nerdy Logs**—a simple application where users can maintain their own watchlists, playlists and reading lists across multiple types of media.

Administrators can expand the shared catalog by adding new items, while users can organize those items into their own personal library. Users can also create custom entries of their own, which can later be personalized with descriptions and cover images.