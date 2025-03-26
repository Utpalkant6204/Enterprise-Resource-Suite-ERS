package com.example.Enterprise.Resource.Suite.ERS.Services.Impl;

import com.example.Enterprise.Resource.Suite.ERS.Entity.ApiRoleMapping;
import com.example.Enterprise.Resource.Suite.ERS.Repositories.ApiRoleMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleAccessServiceImpl {

    private final ApiRoleMappingRepository apiRoleMappingRepository;

    @Autowired
    public RoleAccessServiceImpl(ApiRoleMappingRepository apiRoleMappingRepository) {
        this.apiRoleMappingRepository = apiRoleMappingRepository;
    }

    public List<String> getRolesForEndPoint(String endPoint) {
        Optional<ApiRoleMapping> apiRoleMapping = apiRoleMappingRepository.findByApiEndpoint(endPoint);
        return apiRoleMapping.map(ApiRoleMapping::getRoles).orElse(List.of());
    }
}
