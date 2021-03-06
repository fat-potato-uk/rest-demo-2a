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