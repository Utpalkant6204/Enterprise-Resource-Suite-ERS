package com.example.Enterprise.Resource.Suite.ERS.Mapper;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.EmployeeCreateDTO;
import com.example.Enterprise.Resource.Suite.ERS.Entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeCreateMapper extends GenericMapper<Employee, EmployeeCreateDTO>{
}
