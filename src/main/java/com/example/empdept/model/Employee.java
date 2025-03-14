package com.example.empdept.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // ✅ Prevents serialization issues
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role;
    private double salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // Constructors
    public Employee() {}

    public Employee(String name, String role, double salary, Department department) {
        this.name = name;
        this.role = role;
        this.salary = salary;
        this.department = department;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
