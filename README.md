# Flight-Search API System

## Table of Contents
- [Overview](#overview)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Create the images of the Application using Docker](#create-the-images-of-the-application-using-docker)
- [Accessing Swagger UI](#accessing-swagger-ui)
- [The images of the project on my docker hub](#the-images-of-the-project-on-my-docker-hub)
 

  
## Overview

Flight-Search is a Spring Boot application designed to provide flight search functionality using various technologies and best practices. This project leverages PostgreSQL for database management, Gradle for build automation, and incorporates advanced features like Aspect-Oriented Programming (AOP), JSON Web Tokens (JWT) for security, Kafka for messaging, Swagger for API documentation, JPA and JDBC for data access, and Docker for containerization. Additionally, the project includes third-party API integration, JUnit for unit testing, and comprehensive logging. An H2 in-memory database is also available for testing and development purposes.

## Technologies Used

- **Spring Boot**: Core framework for building the application
- **PostgreSQL**: Relational database management system
- **H2**: In-memory database for testing and development
- **Gradle**: Build automation tool
- **AOP**: Aspect-Oriented Programming for cross-cutting concerns
- **JWT**: JSON Web Tokens for authentication and authorization
- **Kafka**: Messaging system for handling asynchronous communication
- **Swagger**: API documentation tool
- **JPA**: Java Persistence API for ORM (Object-Relational Mapping)
- **JDBC**: Java Database Connectivity for direct database access
- **REST API**: Interface for accessing flight search services
- **Logging**: Slf4j for logging and debugging
- **Third-Party APIs**: Integration with external flight data providers
- **JUnit**: Unit testing framework
- **Docker**: Containerization for consistent deployment

## Prerequisites

- **Java 17**
- **Gradle 8**
- **PostgreSQL database**
- **H2 database**
- **Docker**
- **Apache Kafka**

## Installation
### 1. Clone the repository:
   ```bash
   git clone https://github.com/JehadHamayel/Flight-Search-with-REST-API
   ```
 
### 2. Install Ubuntu WSL2:
Follow the [Ubuntu WSL2 Guide](https://github.com/ubuntu/WSL/blob/main/docs/guides/install-ubuntu-wsl2.md) for setting up Ubuntu on Windows Subsystem for Linux 2.

### 3. Run Kafka on Windows:
Follow these steps to get Kafka running on Windows:

1. **Download and Extract Kafka**:
   - Download Kafka from the official site: [Kafka Downloads](https://kafka.apache.org/quickstart)
   - Extract the files to a folder, e.g., `C:\kafka`.

2. **Start Zookeeper**:
   Kafka relies on Zookeeper. Open a command prompt, navigate to the Kafka directory, and run:
   ```bash
   bin/zookeeper-server-start.sh config/zookeeper.properties
   ```
3. **Start Kafka Server**:
   ```bash
   bin/kafka-server-start.sh config/server.properties
   ```
4. **Set Up Port Forwarding**:
   Kafka on Windows needs port forwarding to communicate with WSL2. Run the following command in an elevated command prompt (Run as Administrator):
   ```bash
   netsh interface portproxy add v4tov4 listenport=9092 listenaddress=0.0.0.0 connectport=9092 connectaddress=<IP OF YOUR WSL2>
   ```

5. **Find Your WSL2 IP Address**:
   To get your WSL2 IP address, run:
   ```bash
   wsl hostname -I
   ```

## Running the Application
- Make sure **Kafka** and **PostgreSQL** are running.

- Build the project using Gradle:
   ```bash
    gradle clean build
   ```
- Run the Spring Boot application 

## Create the images of the Application using Docker
- Using this commad you will pull and create the images for the application:
  ```bash
   docker compose -f .\docker-compose.yaml up -d
  ```
  
## Accessing Swagger UI

Once the application is running, you can access the Swagger UI to explore the REST API but if you want to see the result in web page you have to stop the jwt:

```
http://localhost:8080/swagger-ui/index.html
```
## The images of the project on my docker hub
- [Project Image](https://hub.docker.com/r/jehad950/flight_search_rest_api)
