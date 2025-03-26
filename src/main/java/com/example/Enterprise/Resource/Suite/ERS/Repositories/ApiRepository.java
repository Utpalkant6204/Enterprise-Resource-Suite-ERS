package com.example.Enterprise.Resource.Suite.ERS.Repositories;

import com.example.Enterprise.Resource.Suite.ERS.Entity.ApiRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApiRepository extends JpaRepository<ApiRole, UUID> {
}
