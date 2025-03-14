package com.example.empdept.repository;

import com.example.empdept.model.Department;
import com.example.empdept.model.Employee;
import com.example.empdept.repository.EmployeeRepository;
import com.example.empdept.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Uses real DB instead of H2
@Rollback(false) // Prevents rollback for testing actual DB changes
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testSaveEmployee() {
        Department department = new Department();
        department.setName("IT");
        department = departmentRepository.save(department); // Save Department first

        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setSalary(60000);
        employee.setDepartment(department); // Assign saved department

        Employee savedEmployee = employeeRepository.save(employee);

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }


    @Test
    public void testUpdateEmployee() {
        // Step 1: Save an employee first
        Department department = new Department();
        department.setName("IT");
        department = departmentRepository.save(department);

        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setSalary(50000);
        employee.setDepartment(department);
        Employee savedEmployee = employeeRepository.save(employee);

        // Step 2: Update the employee's salary
        savedEmployee.setSalary(60000);
        employeeRepository.save(savedEmployee); // Save updated employee

        // Step 3: Fetch updated employee
        Optional<Employee> updatedEmployee = employeeRepository.findById(savedEmployee.getId());

        // Step 4: Validate update
        assertThat(updatedEmployee).isPresent();
        assertThat(updatedEmployee.get().getSalary()).isEqualTo(60000); // Ensure salary is updated
    }



    @Test
    public void testFindAllEmployees() {
        // Insert an employee first
        Department department = new Department();
        department.setName("HR");
        department = departmentRepository.save(department);

        Employee employee = new Employee();
        employee.setName("Alice");
        employee.setSalary(55000);
        employee.setDepartment(department);
        employeeRepository.save(employee); // Save new employee

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isNotEmpty(); // Ensure the list is not empty
    }

    @Test
    public void testFindEmployeeById() {
        // Save an employee before finding by ID
        Department department = new Department();
        department.setName("Finance");
        department = departmentRepository.save(department);

        Employee employee = new Employee();
        employee.setName("Bob");
        employee.setSalary(70000);
        employee.setDepartment(department);
        Employee savedEmployee = employeeRepository.save(employee);

        Optional<Employee> foundEmployee = employeeRepository.findById(savedEmployee.getId());
        assertThat(foundEmployee).isPresent();
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = employeeRepository.findById(1L).get();
        employeeRepository.delete(employee);

        Optional<Employee> deletedEmployee = employeeRepository.findById(1L);
        assertThat(deletedEmployee).isEmpty();
    }
}
