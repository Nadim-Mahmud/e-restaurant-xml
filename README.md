# Online Restaurant Application

## Overview
This is an Online restaurant web application built using Java and the Spring Framework. The application provides functionality for users, admins, chefs, and waiters to interact with the system. It follows a layered architecture with controllers, services, DAOs, and entities. The frontend is implemented using JSP, and the backend leverages Spring MVC and Hibernate.

## Features
### User Authentication and Profile Management
- Secure login and registration system.
- Profile updates and password management.
- Role-based authentication for different users (Admin, Chef, Waiter, User).

### Order and Item Management
- Users can browse and order items from the restaurant menu.
- Orders are processed and managed efficiently.
- Items can be added, updated, or removed by authorized users.

### Role-Based Access Control
- Admins can manage users, orders, and restaurant settings.
- Chefs can view and update the cooking status of orders.
- Waiters can manage restaurant tables and assist in order fulfillment.

### Restaurant Table Management
- Manage restaurant table bookings.
- Assign orders to specific tables.
- Ensure table availability and status tracking.

### Validation and Exception Handling
- Input validation for user registration, order details, and form submissions.
- Custom exception handling for error management.

### Encryption and Security Features
- Secure password storage using encryption.
- Protection against common security vulnerabilities.
- Role-based access ensures only authorized users perform specific tasks.

## Technologies Used
- **Backend:** Java, Spring MVC, Hibernate
- **Frontend:** JSP, CSS
- **Database:** SQL
- **Build Tool:** Gradle
- **Security:** Encryption utilities

## Project Structure
```
.
├── README.md
├── build.gradle
├── settings.gradle
├── src
│   ├── main
│   │   ├── java
│   │   │   └── net.therap.estaurant
│   │   │       ├── controller (Handles HTTP requests)
│   │   │       ├── service (Business logic implementation)
│   │   │       ├── dao (Data Access Layer)
│   │   │       ├── entity (Database entities)
│   │   │       ├── exception (Custom exception handling)
│   │   │       ├── filter (Request filters)
│   │   │       ├── propertyEditor (Data conversion utilities)
│   │   │       ├── util (Helper classes, encryption, etc.)
│   │   │       ├── validator (Form and data validation)
│   │   ├── resources
│   │   │   ├── messages.properties (Internationalization support)
│   │   │   ├── META-INF/persistence.xml (Database configuration)
│   │   ├── webapp
│   │       ├── WEB-INF (Spring and JSP configuration)
│   │       ├── assets (CSS, images, and static content)
```

## Setup Instructions
### Prerequisites
- Java 17+
- Gradle
- Database (MySQL/PostgreSQL/SQLite based on configuration)
- Tomcat or any servlet container

### Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/Nadim-Mahmud/e-restaurant-xml.git
   cd e-restaurant-xml
   ```
2. Configure the database in `persistence.xml`.
3. Build the project using Gradle:
   ```sh
   ./gradlew build
   ```
4. Deploy the WAR file to Tomcat or run locally:
   ```sh
   ./gradlew bootRun
   ```
5. Access the application at `http://localhost:8080`.
