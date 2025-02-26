package com.example.Enterprise.Resource.Suite.ERS.Controllers;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.CriteriaDTO;
import com.example.Enterprise.Resource.Suite.ERS.DTOS.EmployeeDTO;
import com.example.Enterprise.Resource.Suite.ERS.DTOS.SearchDTO;
import com.example.Enterprise.Resource.Suite.ERS.Services.Interface.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/v1/_search")
    public ResponseEntity<?> searchEmployee(@RequestBody @Valid SearchDTO searchDTO)
    {
        List<EmployeeDTO> employee = employeeService.searchEmployee(searchDTO);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
