# StockPro – Inventory & Order Management System

StockPro is a web-based **Inventory & Order Management System** developed using **Spring Boot, Thymeleaf, JPA/Hibernate, and MySQL**.

The application helps businesses manage products, categories, suppliers, customers, inventory, orders, sales, reports, invoices, and notifications from a single system.

## Features

* Dashboard with inventory and order overview
* Product management
* Category management
* Supplier management
* Customer management
* Inventory and stock management
* Order management
* Order item management
* Sales tracking
* Reports
* Invoice management
* Low-stock and out-of-stock notifications
* Pending order notifications
* Application settings
* Responsive and modern user interface

## Technologies Used

### Backend

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate

### Frontend

* HTML
* CSS
* JavaScript
* Thymeleaf

### Database

* MySQL

### Tools

* Eclipse
* Maven
* Git
* GitHub

## Project Architecture

```text
Browser
   ↓
Thymeleaf UI
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
MySQL Database
```

## Main Modules

```text
Dashboard
Products
Categories
Suppliers
Customers
Inventory
Orders
Sales
Reports
Invoices
Notifications
Settings
```

## Database Entities

* Product
* Category
* Supplier
* Customer
* Order
* OrderItem

## Project Structure

```text
inventory-management/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/inventory/management/
│   │   │       ├── controller/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   └── images/
│   │       ├── templates/
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## How to Run the Project

### 1. Clone the repository

```bash
git clone https://github.com/dinesh11062005/StockPro-Inventory-Order-Management.git
```

### 2. Open the project

Open the project in **Eclipse** as an existing Maven project.

### 3. Configure MySQL

Create a MySQL database and update your database configuration in:

```text
src/main/resources/application.properties
```

Do not publish your real database password or other sensitive credentials in GitHub.

### 4. Run the application

Run the Spring Boot application:

```text
InventoryManagementApplication.java
```

The application runs on:

```text
http://localhost:1106
```

## Application Screens

The project includes interfaces for:

* Dashboard
* Products
* Categories
* Suppliers
* Customers
* Inventory
* Orders
* Sales
* Reports
* Invoice
* Notifications
* Settings

## Future Improvements

* User authentication and authorization
* Role-based access control
* REST API integration
* Advanced analytics
* Export reports to PDF/Excel
* Cloud deployment
* Automated database backup

## Author

**Dinesh J**

BCA (Computer Applications)

GitHub:
https://github.com/dinesh11062005

Portfolio:
https://dinesh11062005.github.io/Dinesh-J-Portfolio/

## License

This project is created for educational and portfolio purposes.
