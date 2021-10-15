package demo;

import demo.repositories.EmployeeRepository;
import demo.models.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee newEmployee() {
        return employeeRepository.save(new Employee("Conrad", "Junior Software Developer"));
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

//    public void editEmployeeId(Long id, Employee employee) {
//        return employeeRepository.save(employee);
//    }
//
//    public Employee deleteEmployee() {
//
//    }
}
