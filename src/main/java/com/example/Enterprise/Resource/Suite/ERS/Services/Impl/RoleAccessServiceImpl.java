package com.example.Enterprise.Resource.Suite.ERS.Services.Impl;

import com.example.Enterprise.Resource.Suite.ERS.Entity.ApiRole;
import com.example.Enterprise.Resource.Suite.ERS.Entity.ApiRoleMapping;
import com.example.Enterprise.Resource.Suite.ERS.Repositories.ApiRepository;
import com.example.Enterprise.Resource.Suite.ERS.Repositories.ApiRoleMappingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleAccessServiceImpl {

    private final ApiRoleMappingRepository apiRoleMappingRepository;

    private final ApiRepository apiRepository;

    @Autowired
    public RoleAccessServiceImpl(ApiRoleMappingRepository apiRoleMappingRepository, ApiRepository apiRepository) {
        this.apiRoleMappingRepository = apiRoleMappingRepository;
        this.apiRepository = apiRepository;
    }

    public List<String> getRolesForEndPoint(String endPoint) {
        Optional<ApiRoleMapping> apiRoleMapping = apiRoleMappingRepository.findByApiEndpointWithRoles(endPoint);
        return apiRoleMapping
                .map(mapping -> mapping.getRoles().stream()
                        .filter(ApiRole::getIsActive)
                        .map(ApiRole::getRole)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    @Transactional
    public Object addNewEndPoints(ApiRoleMapping apiRoleMapping) {
        apiRoleMapping.getRoles().forEach(role -> role.setApiRoleMapping(apiRoleMapping));
        return apiRoleMappingRepository.save(apiRoleMapping);
    }

    public Object getAllEnpoints() {
        return apiRoleMappingRepository.findAll();
    }
}
