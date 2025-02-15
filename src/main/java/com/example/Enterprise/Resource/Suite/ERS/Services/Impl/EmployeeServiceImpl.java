package com.example.Enterprise.Resource.Suite.ERS.Services.Impl;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.CriteriaDTO;
import com.example.Enterprise.Resource.Suite.ERS.DTOS.EmployeeDTO;
import com.example.Enterprise.Resource.Suite.ERS.DTOS.SearchDTO;
import com.example.Enterprise.Resource.Suite.ERS.Entity.Employee;
import com.example.Enterprise.Resource.Suite.ERS.Mapper.EmployeeMapper;
import com.example.Enterprise.Resource.Suite.ERS.Repositories.EmployeeRepository;
import com.example.Enterprise.Resource.Suite.ERS.Services.Interface.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }


    @Override
    public List<EmployeeDTO> searchEmployee(SearchDTO searchDTO) {
        CriteriaDTO criteriaDTO = searchDTO.getCriteriaDTO();

        try {
            log.info("Searching employees with criteria: ");
            List<Employee> employees = employeeRepository.searchEmployee(
                    criteriaDTO.getId(),
                    criteriaDTO.getSearchText(),
                    criteriaDTO.getRole(),
                    criteriaDTO.getPagination().getPageSize(),
                    criteriaDTO.getPagination().getPageNumber()
            );

            log.info("Found {} employees", employees.size());
            return employees.stream()
                    .map(employeeMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while searching employees: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
