package com.example.Enterprise.Resource.Suite.ERS.Repositories;

import com.example.Enterprise.Resource.Suite.ERS.Entity.ApiRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApiRoleMappingRepository extends JpaRepository<ApiRoleMapping, UUID> {
    @Query("SELECT a FROM ApiRoleMapping a JOIN FETCH a.roles WHERE a.apiEndpoint = :apiEndpoint AND a.isApiActive = true")
    Optional<ApiRoleMapping> findByApiEndpointWithRoles(@Param("apiEndpoint") String apiEndpoint);

}
