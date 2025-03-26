package com.example.Enterprise.Resource.Suite.ERS.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "api_roles")
@Getter
@Setter
public class ApiRole {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "api_id", nullable = false)
    private ApiRoleMapping apiRoleMapping;

    public ApiRole() {}

    public ApiRole(String role, Boolean isActive, ApiRoleMapping apiRoleMapping) {
        this.role = role;
        this.isActive = isActive;
        this.apiRoleMapping = apiRoleMapping;
    }
}
