package com.example.empdept.service;

import com.example.empdept.model.Employee;
import com.example.empdept.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll(); // Ensure this is returning unique values
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getEmployeesBySalaryRange(double minSalary, double maxSalary) {
        return employeeRepository.findBySalaryBetween(minSalary, maxSalary);
    }
}
