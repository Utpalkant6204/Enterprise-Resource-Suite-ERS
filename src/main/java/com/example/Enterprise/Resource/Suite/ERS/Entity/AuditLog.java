package com.example.Enterprise.Resource.Suite.ERS.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Column(nullable = false)
    private String action;

    @ManyToOne
    @JoinColumn(name = "performed_by")
    private Employee performedBy;

    private LocalDateTime performedAt = LocalDateTime.now();

    public AuditLog() {
    }

    public AuditLog(Long logId, String action, Employee performedBy, LocalDateTime performedAt) {
        this.logId = logId;
        this.action = action;
        this.performedBy = performedBy;
        this.performedAt = performedAt;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Employee getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(Employee performedBy) {
        this.performedBy = performedBy;
    }

    public LocalDateTime getPerformedAt() {
        return performedAt;
    }

    public void setPerformedAt(LocalDateTime performedAt) {
        this.performedAt = performedAt;
    }
}
