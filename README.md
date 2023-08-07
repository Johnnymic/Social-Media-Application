# Social Media Application - Readme

## Overview

The Social Media Application is a Restful service-based web application built using Java JDK 17, Spring Boot, Spring Security, Spring Validation, PostgreSQL, and Mockito. It enables users to connect, share posts, comment on posts, like posts, and follow other users. The application is designed to be scalable, secure, and maintainable.

## Architecture Choices

1. **Java JDK 17**: Java JDK 17 is used as the programming language and runtime environment for the application. It provides the latest features and improvements in the Java language and runtime.

2. **Spring Boot**: Spring Boot is used to create a standalone, production-ready application with minimal configuration. It provides an embedded web server and simplifies the setup of various Spring modules.

3. **Spring Security**: Spring Security is employed for user authentication and authorization. It secures the API endpoints, ensuring that only authorized users can access specific resources.

4. **Spring Validation**: Spring Validation is used to enforce data integrity and validate user inputs. It helps prevent invalid data from entering the system and improves the overall reliability of the application.

5. **PostgreSQL**: PostgreSQL is chosen as the relational database for storing user information, posts, and other data. It provides robust data storage and supports ACID transactions.

6. **Mockito**: Mockito is used for testing purposes. It allows us to create mock objects and conduct unit testing to ensure the application's functionality and behavior.

## Challenges Encountered

During the development of the Social Media Application, we encountered several challenges:

1. **Integration with PostgreSQL**: Integrating the application with PostgreSQL involved configuring the database connection, setting up the schema, and ensuring proper data management. We also needed to handle data migration and versioning.

2. **Mockito Testing**: Writing effective unit tests using Mockito required understanding the principles of mock-based testing and ensuring that the tests cover critical functionalities of the application.

3. **Data Validation**: Ensuring data integrity and preventing invalid data from entering the system required careful validation of user inputs. We implemented Spring Validation to handle data validation and provide meaningful error messages to users.

4. **User Authentication and Authorization**: Implementing a robust user authentication and authorization system was a critical challenge. We needed to ensure secure password storage, token-based authentication, and proper access control to protect sensitive user data and resources.

5. **Scalability**: Designing the application to handle a large number of users and posts while maintaining performance was an important consideration. We optimized database queries and employed caching mechanisms to improve scalability.

6. **Testing and Quality Assurance**: Thorough testing and quality assurance were vital to identify and fix potential bugs and vulnerabilities. We utilized unit testing with Mockito, integration testing, and manual testing to ensure the application's reliability and security.

Overall, by leveraging the power of Java JDK 17, Spring Boot, and associated Spring modules, integrating PostgreSQL for data storage, and using Mockito for testing, we successfully built a scalable, secure, and efficient Social Media Application.

## Prerequisites

Before running the Social Media Application, ensure you have the following software installed on your machine:

- Java JDK 17: [https://www.oracle.com/java/technologies/javase-downloads.html](https://www.oracle.com/java/technologies/javase-downloads.html)
- PostgreSQL: [https://www.postgresql.org/download/](https://www.postgresql.org/download/)

## Installation

To set up and run the application, follow these steps:

1. Clone the repository from [https://gith/social-media-app.git](https://github.com/your_username/social-media-app.git).

2. Change into the project directory:

   ```bash
   cd social-media-app
   ```

3. Build and run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

The application will be accessible at [http://localhost:8080](http://localhost:8080).

For API documentation and available endpoints, refer to the [Endpoints](#endpoints) section in this readme.

## Contributing

We welcome contributions to the Social Media Application. To contribute, follow the steps mentioned in the [Contributing](#contributing) section.

## License

The Social Media Application is released under the [MIT License](LICENSE). Feel free to use, modify, and distribute the code as per the terms of the license.

---

Thank you for using the Social Media Application! If you have any questions or need assistance, please feel free to contact us at support@example.com. Happy socializing!
