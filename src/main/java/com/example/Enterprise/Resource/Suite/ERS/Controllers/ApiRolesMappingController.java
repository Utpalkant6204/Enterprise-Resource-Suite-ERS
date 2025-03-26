package com.example.Enterprise.Resource.Suite.ERS.Controllers;

import com.example.Enterprise.Resource.Suite.ERS.Entity.ApiRoleMapping;
import com.example.Enterprise.Resource.Suite.ERS.Exceptions.CustomException;
import com.example.Enterprise.Resource.Suite.ERS.Services.Impl.RoleAccessServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiRolesMappingController {

    private final RoleAccessServiceImpl roleAccessService;

    @Autowired
    public ApiRolesMappingController(RoleAccessServiceImpl roleAccessService) {
        this.roleAccessService = roleAccessService;
    }

    @PostMapping("/v1/add")
    public ResponseEntity<ApiRoleMapping> addNewApi(@Valid @RequestBody ApiRoleMapping apiRoleMapping) {
        if (apiRoleMapping.getRoles() == null || apiRoleMapping.getRoles().isEmpty()) {
            throw new CustomException("Roles cannot be empty");
        }
        ApiRoleMapping savedApiRoleMapping = (ApiRoleMapping) roleAccessService.addNewEndPoints(apiRoleMapping);
        return new ResponseEntity<>(savedApiRoleMapping, HttpStatus.CREATED);
    }

    @GetMapping("/v1/get_all")
    public ResponseEntity<?> updateApi(){
        return new ResponseEntity<>(roleAccessService.getAllEnpoints(), HttpStatus.OK);
    }
}
