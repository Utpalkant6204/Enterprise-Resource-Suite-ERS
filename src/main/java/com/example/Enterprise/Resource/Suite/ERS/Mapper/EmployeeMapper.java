package com.example.Enterprise.Resource.Suite.ERS.Mapper;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.EmployeeDTO;
import com.example.Enterprise.Resource.Suite.ERS.Entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends GenericMapper<Employee, EmployeeDTO>{
}
