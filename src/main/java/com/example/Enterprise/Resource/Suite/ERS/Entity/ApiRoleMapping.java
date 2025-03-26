package com.example.Enterprise.Resource.Suite.ERS.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "api_role_mapping")
@Getter
@Setter
public class ApiRoleMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_endpoint", unique = true, nullable = false)
    private String apiEndpoint;

    @ElementCollection
    @CollectionTable(name = "api_roles", joinColumns = @JoinColumn(name = "api_id"))
    @Column(name = "role")
    private List<String> roles;
}

