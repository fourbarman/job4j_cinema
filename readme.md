# job4j_cinema
Repository for "cinema" project:

Web app with Spring boot, Thymeleaf, Bootstrap, JDBC.


This project is based on [JDK 17](https://www.oracle.com/java/technologies/javase-downloads.html#JDK17) and uses:
- [Maven](https://maven.apache.org/) (v. 3.6.3),
- [Springboot](https://spring.io/) (v. 2.5.2),
- [Bootstrap](https://getbootstrap.com/docs/4.4/getting-started/introduction/) (v. 4.4.1)
- [Thymeleaf](https://www.thymeleaf.org/)

The database layer is represented by:
- JDBC,
- [PostgreSQL](https://www.postgresql.org/) (v. 42.2),
- [Liquibase](https://www.liquibase.org/) (v. 3.6.2).

Tests use [JUnit5](https://junit.org/junit5/) and [Mockito](https://site.mockito.org/).

## articles
- registration and authentication.
- main page with choosing cinema, row and seat (shows only free seat);
- user can purchase ticket.

## build and start
Steps to start a program from sources:
1. Create database with name "cinema".
2. Database user should be **postgres** with **password** password.
3. Go through terminal\cmd to project sources and use maven command to generate .jar:
```mvn package```
4. If the project compiles successfully You will see folder "target" with generated _job4j_cinema-1.0-SNAPSHOT.jar_.
5. Execute it from terminal\cmd with command:
```java -jar job4j_cinema-1.0-SNAPSHOT.jar```
6. Final step is just open Your browser and head to project index page: **http://localhost:8080/index**.

### TODO list
- [ ] Cover project code with unit tests.
- [ ] Add proper JavaDoc.

### Contacts
Feel free for contacting me:
- **Skype**: pankovmv
