package com.example.Enterprise.Resource.Suite.ERS.Controllers;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.EmployeeDTO;
import com.example.Enterprise.Resource.Suite.ERS.Services.Interface.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/v1/register_employee")
    public ResponseEntity<?> createEmplyee(@Valid @RequestBody EmployeeDTO employeeDTO) throws Exception {
        EmployeeDTO employeeDTO1 = adminService.createEmployee(employeeDTO);
        return new ResponseEntity<>(employeeDTO1, HttpStatus.CREATED);
    }
}
