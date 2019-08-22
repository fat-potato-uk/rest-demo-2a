### Challenge 2

Here, you'll build an application that can save an Employee (name and role) to an in memory database. 
Upon startup, a bean will add the following to the database:
```
Bilbo Baggins : Burglar
Frodo Baggins : Thief
```

So, to start, lets look at the `pom.xml` for the bits we will need:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>rest-demo-2</groupId>
    <artifactId>rest-demo-2</artifactId>
    <version>1.0-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.6.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>
        <dependency>
            <groupId>com.jayway.jsonpath</groupId>
            <artifactId>json-path</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.2</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <properties>
        <java.version>12</java.version>
    </properties>


    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>


</project>
```

This is nearly identical to the last challenge, but with the inclusion of:

```xml
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>
```

This is an in memory database that can (and often is) used for testing (and production for the brave). There is some 
controversy around using "real" databases for testing, for good reason. They can prove heavy weight and often go against
the principles of "unit" tests. Feel free to ask me to rant more on this point, but for now, they will help with our challenge.

Next, lets create an entity that will be used to save the employee details to the database:

```java
package demo.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@RequiredArgsConstructor
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private final String name;
    private final String role;
}
```

So, a few points of interest in here. The `@Data` is used as before, but now we have a few extra class and
field annotations.

* `@Entity`: This tells Spring JPA that this is an object that it needs to manage
* `@RequiredArgsCOnstructor`: This is another `Lombok` annotation that creates a constructor
 for all "required" arguments (in this case, the `final` fields)
* `@Id`: This indicates that this field is the primary key for the entity
* `@GeneratedValue`: This tells Spring JPA to create an auto-incrementing key for this value

Next we will need to save this entity some how. Thankfully, in Spring Boot, this is very easy:

```java
package demo.repositories;

import demo.models.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    long countEmployeeByName(String name);
}
```

And that's it. This interface will have a concrete implementation automatically generated for it at run time.
This allows us to wire in a `EmployeeRepository` bean where we need to save an `Employee` and Spring takes care
of the rest.

If we need to query this data, we can use a function similar to the one given above (`countEmployeeByName`).
Spring provides a `find|count|By|Field|Where` type syntax for common query behaviours. If more complex
queries are required, `HQL` can be used with a `Query(...)` annotation over a method signature in 
the interface (but this is rare). 

Now, to test this, we are going to create some entries on startup:

```java
package demo;

import demo.models.Employee;
import demo.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class Config {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
        };
    }
}
```

This is doing a little bit of black magic. We are defining a `CommandLineRunner` bean as part of our Spring
`@Configuration`. This will be loaded on start of our application as part of the context initialisation.

Meaning we also need a entry point as before:

```java
package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
```

Now, to test this. You will need to create a test, and have it load the Spring context. This can be done with
the following (class) annotations:

```java
@RunWith(SpringRunner.class)
@SpringBootTest
```

In addition, you will need to wire the `EmployeeRepository`, this can be done by:

```java
@Autowired
private EmployeeRepository employeeRepository;
```
 
For example:

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSetupRepoCount() {
        assertEquals(...);
    }
}
```
Try to test both the total number of entries in the database and the number for each named individual.

Good luck!
