package com.example.Enterprise.Resource.Suite.ERS.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "timesheet")
public class Timesheet {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID timesheetId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(nullable = false)
    private Double hoursWorked;

    @Column(nullable = false)
    private LocalDate workDate;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Timesheet() {
    }

    public Timesheet(UUID timesheetId, Employee employee, Task task, Double hoursWorked, LocalDate workDate, LocalDateTime createdAt) {
        this.timesheetId = timesheetId;
        this.employee = employee;
        this.task = task;
        this.hoursWorked = hoursWorked;
        this.workDate = workDate;
        this.createdAt = createdAt;
    }

    public UUID getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(UUID timesheetId) {
        this.timesheetId = timesheetId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
