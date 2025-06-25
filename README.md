# ARS Authentication Application – Spring Boot + MySQL + Dockerized Setup

A production-ready **Spring Boot** application packaged as a **standalone JAR** (with embedded Tomcat), 
using **MySQL** for database persistence.

This application is containerized via Docker with persistent volumes for database.

---

## Project structure & Build

### Prepare
- **Java**: JDK 17
- **Spring Boot**: 3.1.5
- **Build Tool**: Maven 3.8.5
- **Application**: Built as a **standalone JAR** with embedded Tomcat
- **Database**: Uses MySQL 8.0.37 as DBMS database (in separate container)

### Build & Packaging
- Maven profile: `prod`
- Output: `target/ars-auth-service-<version>.jar`

    ```bash
    mvn clean package -Pprod -DskipTests
    ```

---


## Maintainer
Created by **Công Thoại**  
Email: [dcthoai1023@gmail.com](mailto:dcthoai1023@gmail.com)
