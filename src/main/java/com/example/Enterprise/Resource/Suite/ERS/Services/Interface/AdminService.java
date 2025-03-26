package com.example.Enterprise.Resource.Suite.ERS.Services.Interface;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.EmployeeDTO;

public interface AdminService {
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) throws Exception;
}
