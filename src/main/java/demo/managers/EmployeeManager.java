package demo.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.repositories.EmployeeRepository;
import demo.models.Employee;

import java.util.*;

@Service
public class EmployeeManager {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }
//    ...
}

