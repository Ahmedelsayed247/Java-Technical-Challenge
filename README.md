# Java Technical Challenge

This repository contains a Java-based technical challenge implementation. The application provides user registration and contact management functionality, implemented with Spring Boot and MySQL, and is secured using JWT authentication.

## 📄 Project Overview

The goal of this application is to provide a simple backend API for user registration and contact management. The system allows users to register, authenticate via JWT, and manage multiple contacts. The application follows best practices such as clean architecture, validation annotations, and custom exception handling.

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
## 📄 Prerequisites
- Java 11 or higher: Required to run the Spring Boot application.
- MySQL: The database used for storing user and contact data.
- Maven: A build tool to handle dependencies and run the application.
- IDE (optional): IntelliJ IDEA, Eclipse, or any Java-compatible IDE.

# # 📦 Installation
## 1- Clone the Repository
```bash
git clone https://github.com/Ahmedelsayed247/Java-Technical-Challenge.git
```
## 2- Set Up MySQL Database
Ensure that MySQL is installed and running on your local machine. Create a new database for the application, 
```sql
CREATE DATABASE addressbook_db;
```

## Configure Application Properties
```propeties
spring.datasource.url=jdbc:mysql://localhost:3306/addressbook_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# Hibernate Configuration
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
# JWT Configuration
jwt.secret=thisIsMysecregtfrdesww233eggtffeeddgkjjhhtdhttebd54ndhdhfhhhshs8877465sbbdd
jwt.expiration=3600000 

```
## Build the Project
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
- POST /api/auth/register: Register a new user.

- POST /api/auth/login: Login and obtain a JWT token.

- GET /api/contacts: Get a list of contacts for the authenticated user.

- POST /api/contacts: Create a new contact.

- PUT /api/contacts/{id}: Update a contact.

- DELETE /api/contacts/{id}: Delete a contact.


## 🧑‍💻 Usage Usage
- User Registration: Use the /api/auth/register endpoint to create a new user by providing a username, password, and email.
- User Login: Once registered, login through the /api/auth/login endpoint to get a JWT token.
- Managing Contacts: After login, use the /api/contacts endpoints to manage your contacts (CRUD operations).

## Example Requests
## Register User:
``` bash 
POST /api/auth/register
{
  "username": "ahmedelsayed",
  "password": "ahmed123456",
  "confirmPassword": "ahmed123456"
}
```
## Login User:

``` bash 
POST /api/auth/login
{
  "username": "ahmedelsayed",
  "password": "ahmed123456"
}
```
## Create Contact (requires JWT token in the Authorization header):
``` bash 
POST /api/contacts
{
  "name": "Jane Doe",
  "phone": "123-456-7890",
  "email": "jane.doe@example.com"
}
```
