# Banking Management System

This project is a simple **Banking Management System** built using **Java** and **JDBC** that allows users to register accounts, check balances, deposit, withdraw, and transfer money. It connects to a **MySQL** database to perform all operations.

---

## Features

- **User Registration and Login**
- **Account Creation**
- **Check Balance**
- **Deposit Money**
- **Withdraw Money**
- **Transfer Money (Between Accounts)**
- **Secure Credential Handling**
- **Basic Console UI**

---

## Technologies Used

- **Java** (Core Java, OOP)
- **JDBC (Java Database Connectivity)**
- **MySQL** (Relational database)

---

## Requirements

- Java JDK 8 or above
- MySQL Server
- MySQL JDBC Driver (Connector/J)

---

## Database Setup

### SQL Script

You can find the database script in the `SQL_Scripts` folder.

📁 **File:** [SQL_Scripts/setup.sql](SQL_Scripts/setup.sql)

Run this script in your MySQL Server to create necessary tables like `users`, `accounts`, `transactions`, etc.

---

## Java Files

All Java source files are located in the `Java_Files` folder:

📁 **Files**:
- [Java_Files/Driver.java](Java_Files/Driver.java)
- [Java_Files/User.java](Java_Files/User.java)
- [Java_Files/Account.java](Java_Files/Account.java)
- [Java_Files/Transaction.java](Java_Files/Transaction.java)

Update the DB credentials in `Driver.java` if needed:
```java
private static final String url = "jdbc:mysql://localhost:3306/bank_db";
private static final String username = "root";
private static final String password = "root";
