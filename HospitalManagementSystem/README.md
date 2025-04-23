# Hospital Management System

This project is a simple **Hospital Management System** built using **Java** and **JDBC** to manage hospital data, including patient records, doctor details, and hospital departments. The system interacts with a MySQL database to perform CRUD (Create, Read, Update, Delete) operations for managing various entities.

## Features

- **Patient Management**: Allows the registration and management of patient records.
- **Doctor Management**: Allows adding, updating, and managing doctor details.
- **Department Management**: Manages hospital departments and their information.
- **Database Operations**: Demonstrates CRUD operations with MySQL database using JDBC.
- **User Interface**: Basic text-based menu system to interact with the database.

## Technologies Used

- **Java**: Core Java for backend logic and JDBC for database interaction.
- **MySQL**: For storing and managing hospital data.
- **JDBC (Java Database Connectivity)**: Used to connect Java to MySQL for executing SQL queries.

## Database Setup

### SQL Script

Run the following SQL script to set up the required database and tables:

```sql
CREATE DATABASE hospital_db;

USE hospital_db;

-- Create Patients table
CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    address VARCHAR(255),
    phone VARCHAR(15)
);

-- Create Doctors table
CREATE TABLE doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    specialty VARCHAR(100),
    phone VARCHAR(15)
);

-- Create Departments table
CREATE TABLE departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    location VARCHAR(100)
);

## Database Setup

### SQL Script

You can set up the database by running the SQL script located in the **SQL_Scripts** folder:

**File:** [SQL_Scripts/setup.sql](SQL_Scripts/setup.sql)
