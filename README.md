# Employee Management System (EMS)

A layered **Spring Web MVC** application for managing employee records, built with **Spring Boot 3.4**, **Thymeleaf**, **PostgreSQL**, **Flyway**, and **Lombok**. Designed with clean separation of concerns, DTO-based data contracts, server-side validation, and versioned SQL migrations.

---

## Features

- **Full CRUD Operations:** Create, view, update, and delete employee records using the Post/Redirect/Get (PRG) pattern and `POST`-based deletion.
- **Search & Filtering:** Case-insensitive search across first name, last name, and department.
- **Validation & Data Integrity:** Field-level Jakarta Bean Validation (`@Valid`) paired with duplicate email checks at both the service and database constraint levels.
- **Global Exception Handling:** Centralized `@ControllerAdvice` (`GlobalExceptionHandler`) rendering custom error pages for missing resources and unexpected failures.
- **Versioned Database Migrations:** Automated schema creation and initial data seeding via Flyway, validated at startup by Hibernate (`ddl-auto=validate`).
- **Responsive UI:** Server-side rendered Thymeleaf templates styled with Bootstrap 5, including inline validation feedback and flash notifications.

---

## Architecture

The application follows a strict multi-layered MVC structure:

- **Controller Layer (`controller`)** – Handles HTTP routing, request binding, validation checks, and view resolution.
- **Service Layer (`service`, `service.impl`)** – Encapsulates business logic, duplicate checks, and `@Transactional` boundaries behind an interface contract.
- **DTO & Mapper Layer (`dto`, `mapper`)** – Decouples web form payloads from JPA entities to prevent mass-assignment issues and keep persistence models clean.
- **Repository Layer (`repository`)** – Interfaces with PostgreSQL via Spring Data JPA derived queries.
- **Persistence & Migration Layer (`entity`, `db/migration`)** – Defines relational mappings and version-controlled SQL scripts.

---

## Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.4, Spring Web MVC |
| **Frontend** | Thymeleaf, HTML5, Bootstrap 5 |
| **Persistence** | Spring Data JPA (Hibernate) |
| **Database** | PostgreSQL |
| **Migrations** | Flyway (`flyway-core`, `flyway-database-postgresql`) |
| **Utilities** | Project Lombok, Jakarta Bean Validation |
| **Build Tool** | Maven |

---

## Project Structure

```text
employee-management-system/
├── pom.xml
└── src/main/
    ├── java/com/company/ems/
    │   ├── EmsApplication.java
    │   ├── controller/
    │   │   └── EmployeeController.java
    │   ├── dto/
    │   │   └── EmployeeDto.java
    │   ├── entity/
    │   │   └── Employee.java
    │   ├── exception/
    │   │   ├── DuplicateResourceException.java
    │   │   ├── GlobalExceptionHandler.java
    │   │   └── ResourceNotFoundException.java
    │   ├── mapper/
    │   │   └── EmployeeMapper.java
    │   ├── repository/
    │   │   └── EmployeeRepository.java
    │   └── service/
    │       ├── EmployeeService.java
    │       └── impl/
    │           └── EmployeeServiceImpl.java
    └── resources/
        ├── application.properties
        ├── db/migration/
        │   ├── V1__create_employees_table.sql
        │   └── V2__seed_initial_employees.sql
        └── templates/
            ├── employees/
            │   ├── form.html
            │   └── list.html
            └── error/
                └── custom-error.html
```

---

## Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/employees` | List all employees (supports `?keyword=` search) |
| `GET` | `/employees/new` | Render the create employee form |
| `POST` | `/employees` | Validate and create a new employee |
| `GET` | `/employees/{id}/edit` | Render the edit form for an existing employee |
| `POST` | `/employees/{id}` | Validate and update an existing employee |
| `POST` | `/employees/{id}/delete` | Delete an employee by ID |

---

## Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven 3.8+**
- **PostgreSQL 14+**

### 1. Clone the Repository

```bash
git clone [https://github.com/](https://github.com/)<your-username>/employee-management-system.git
cd employee-management-system
```

### 2. Create the Database

PostgreSQL does not auto-create databases via JDBC URLs. Create `ems_db` once before running the application:

```sql
CREATE DATABASE ems_db;
```

### 3. Configure Credentials

Update `src/main/resources/application.properties` with your local PostgreSQL credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ems_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

On startup, Flyway automatically executes:
- `V1__create_employees_table.sql` — creates the `employees` table with primary key and unique email constraints.
- `V2__seed_initial_employees.sql` — inserts initial sample records.

Once running, open:

```text
http://localhost:8080/employees
```

---

## IDE Configuration (Lombok)

If your IDE flags generated getters, setters, or builders as unresolved:
- **Spring Tool Suite (STS) / Eclipse:** Install Lombok into your IDE by running `java -jar lombok.jar` and restarting.
- **IntelliJ IDEA:** Enable `Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors -> Enable annotation processing`.
- **VS Code:** Install the *Lombok Annotations Support for VS Code* extension.
