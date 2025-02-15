package com.example.Enterprise.Resource.Suite.ERS.Services.Interface;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.EmployeeDTO;
import com.example.Enterprise.Resource.Suite.ERS.DTOS.SearchDTO;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeDTO> searchEmployee(SearchDTO searchDTO);
}
