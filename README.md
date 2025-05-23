# Java Technical Challenge

This repository contains a Java-based technical challenge implementation. The application provides user registration and contact management functionality, implemented with Spring Boot and MySQL, and is secured using JWT authentication.

## 📄 Project Overview

The goal of this application is to provide a simple backend API for user registration and contact management. The system allows users to register, authenticate via JWT, and manage multiple contacts. The application follows best practices such as clean architecture, validation annotations, custom exception handling, and supports pagination for contact listing.


## 🏗️ Project Structure
The project follows a clean architecture, separating concerns into layers to ensure maintainability and scalability. Below is an overview of the directory structure:
```plaintext
com.example.challenge/
├── application                    # Application Layer (Service Layer)
│   ├── service                    # Service classes like AuthService
│   └── exception                  # Custom exceptions
│
├── domain                         # Domain Layer (Entities)
│   ├── model                      # Domain models like User, Contacts
│   └── repository                 # Interfaces for repositories
│
├── infrastructure                 # Infrastructure Layer
│   ├── security                   # Security-related code like JWT provider
│   └── configuration              # Spring Boot configuration classes
│
└── web                            # Presentation Layer (Controllers, DTOs)
    ├── controller                 # Spring MVC controllers like AuthController
    └── dto                        # Data Transfer Objects (DTOs)
├── ChallengeApplication.java  # Main class entry point

```
## 📽️ Demo Video
Watch a short demo of the project here:  
👉 [Watch Video](https://drive.google.com/file/d/1l6LL7_1s3xlN8UxyQtMj4mwjbVxefAEV/view?usp=sharing)

## 📄 Prerequisites
- Java 11 or higher: Required to run the Spring Boot application.
- MySQL: The database used for storing user and contact data.
- Maven: A build tool to handle dependencies and run the application.
- Docker (optional, for containerized setup)
- IDE (optional): IntelliJ IDEA, Eclipse, or any Java-compatible IDE.

## 📦 Installation
## 1- Clone the Repository
```bash
git clone https://github.com/Ahmedelsayed247/Java-Technical-Challenge.git
```
## 2- Set Up MySQL Database
Ensure that MySQL is installed and running on your local machine. Create a new database for the application, 
```sql
CREATE DATABASE addressbook_db;
```
## 🐳 Docker Setup (Optional)
This project supports Docker to simplify setup and deployment.
1. Build and Run with Docker Compose
```bash
docker-compose up --build
```
This command will:
- Build the Spring Boot application image.
- Set up a MySQL container.
- Start everything in a connected environment.
  
2. Access the Application 
- API Base URL: http://localhost:8080
- MySQL: localhost:3306, user: test, password: 123456

3. Docker Files Overview
- Dockerfile: Defines the Spring Boot application image.
- docker-compose.yml: Sets up the app with a MySQL container.


## Build the Project(without docker)
```bash
cd Java-Technical-Challenge
mvn clean install
```
## 🚀 Running the Application
```bash
mvn spring-boot:run
```
The application will start on http://localhost:8080.

## Accessing the API
The following endpoints are available for interacting with the application:
- POST /auth/register: Register a new user.

- POST /auth/login: Login and obtain a JWT token.

- POST /contacts: Create a new contact.

- GET /contacts: Get a list of contacts for the authenticated user (with pagination support).

- GET /contacts/{id}: Get a contact by id.

- DELETE /contacts/{id}: Delete a contact.


## 🧑‍💻 Usage Usage
- User Registration: Use the /auth/register endpoint to create a new user by providing a username, password, and email.
- User Login: Once registered, login through the /auth/login endpoint to get a JWT token.
- Managing Contacts: After login, use the /contacts endpoints to manage your contacts (CRUD operations).

## ⚙️ Pagination Support
- The /contacts GET endpoint supports pagination and sorting using a structured request body via the ContactListRequest DTO. This allows clients to define the page number, size, sorting fields, and direction.
## ContactListRequest Structure:
```json
{
  "page": 0,
  "size": 10,
  "sortBy": ["firstName"],
  "sortDir": "asc"
}
```
- page: (integer) Page number (starting from 0).

- size: (integer) Number of items per page.

- sortBy: (list) One or more fields to sort by (e.g., "firstName", "lastName").

- sortDir: (string) Direction of sorting: "asc" or "desc".```

## Example Requests
## Register User:
``` bash 
POST /auth/register
{
  "username": "ahmedelsayed",
  "password": "ahmed123456",
  "confirmPassword": "ahmed123456"
}
```
## Login User:
``` bash 
POST /auth/login
{
  "username": "ahmedelsayed",
  "password": "ahmed123456"
}
```

## Create Contact (requires JWT token in the Authorization header):
``` bash 
POST /contacts
Authorization: Bearer <JWT_TOKEN>
   {
        "firstName": "ahmed",
        "lastName": "el sayed",
        "phoneNumber": "01030228260",
        "emailAddress": "ahmedelsayedabdelaty4@gmail.com",
        "birthdate": "2001-07-24"
  }
```
## list of contacts for the authenticated user with pagination. 
``` bash 
GEt /contacts
Authorization: Bearer <JWT_TOKEN>
{
  "page": 1,
  "size": 5,
  "sortBy": ["birthdate"],
  "sortDir": "desc"
}
```




