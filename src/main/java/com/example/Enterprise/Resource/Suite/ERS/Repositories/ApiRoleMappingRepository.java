package com.example.Enterprise.Resource.Suite.ERS.Repositories;

import com.example.Enterprise.Resource.Suite.ERS.Entity.ApiRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiRoleMappingRepository extends JpaRepository<ApiRoleMapping, Long> {
    Optional<ApiRoleMapping> findByApiEndpoint(String apiEndpoint);
}
