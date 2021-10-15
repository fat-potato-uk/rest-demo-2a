package demo.controllers;

import demo.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.MediaType;

import demo.models.Employee;
//import demo.managers.EmployeeManager;

import java.util.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/employees")
class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    @Autowired
    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @PostMapping("")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return employeeService.newEmployee();
    }

    @GetMapping("/{id}")
    Optional<Employee> getEmployee(@PathVariable Long id) throws Exception {
        return employeeService.getEmployeeById(id);
    }
//
//    @PutMapping("/{id}")
//    Employee replaceOrCreateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) { ... }
//
//    @DeleteMapping("/{id}")
//    void deleteEmployee(@PathVariable Long id) { ... }
}
