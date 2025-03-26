package com.example.Enterprise.Resource.Suite.ERS.Services.Interface;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.EmployeeCreateDTO;
import com.example.Enterprise.Resource.Suite.ERS.DTOS.EmployeeDTO;

public interface AdminService {
    public EmployeeDTO createEmployee(EmployeeCreateDTO employeeDTO) throws Exception;
}
