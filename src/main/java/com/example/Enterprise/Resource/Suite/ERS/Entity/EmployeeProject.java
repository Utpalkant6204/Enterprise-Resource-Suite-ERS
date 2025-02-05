package com.example.Enterprise.Resource.Suite.ERS.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_project")
public class EmployeeProject {

    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private String role;

    private LocalDateTime assignedAt = LocalDateTime.now();

    public EmployeeProject() {
    }

    public EmployeeProject(Employee employee, Project project, String role, LocalDateTime assignedAt) {
        this.employee = employee;
        this.project = project;
        this.role = role;
        this.assignedAt = assignedAt;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}
