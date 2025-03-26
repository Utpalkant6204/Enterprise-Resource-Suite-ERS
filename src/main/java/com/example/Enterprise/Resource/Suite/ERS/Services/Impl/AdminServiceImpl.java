package com.example.Enterprise.Resource.Suite.ERS.Services.Impl;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.EmployeeCreateDTO;
import com.example.Enterprise.Resource.Suite.ERS.DTOS.EmployeeDTO;
import com.example.Enterprise.Resource.Suite.ERS.Entity.Employee;
import com.example.Enterprise.Resource.Suite.ERS.Exceptions.CustomException;
import com.example.Enterprise.Resource.Suite.ERS.Mapper.EmployeeCreateMapper;
import com.example.Enterprise.Resource.Suite.ERS.Mapper.EmployeeMapper;
import com.example.Enterprise.Resource.Suite.ERS.Repositories.EmployeeRepository;
import com.example.Enterprise.Resource.Suite.ERS.Services.Interface.AdminService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final EmployeeCreateMapper employeeCreateDTO;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, EmployeeCreateMapper employeeCreateDTO, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.employeeCreateDTO = employeeCreateDTO;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public EmployeeDTO createEmployee(EmployeeCreateDTO employeeDTO) {
        log.info("Attempting to save employee: {}", employeeDTO);

        try {
            if(employeeDTO.getPassword() == null)
            {
                log.error("Password is needed while create new employee user");
                throw new CustomException("Password is needed while create new employee user");
            }

            Employee employee = employeeCreateDTO.toEntity(employeeDTO);
            log.debug("Employee entity mapped: {}", employee);

            // encode the password
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));

            // Save to DB
            Employee savedEmployee = employeeRepository.save(employee);
            log.info("Employee saved successfully with ID: {}", savedEmployee.getEmployeeId());

            return employeeMapper.toDto(savedEmployee);

        } catch (ConstraintViolationException cve) {
            log.error("Validation error while saving employee: {}", cve.getMessage(), cve);
            throw new CustomException("Invalid employee data provided", cve);

        } catch (DataIntegrityViolationException dive) {
            log.error("Database integrity violation: {}", dive.getMessage(), dive);
            throw new CustomException("Duplicate entry or data constraint violation", dive);

        } catch (DataAccessException dae) {
            log.error("Database access error while saving employee: {}", dae.getMessage(), dae);
            throw new CustomException("Database error occurred while saving employee", dae);

        } catch (Exception e) {
            log.error("Unexpected error while saving employee: {}", e.getMessage(), e);
            throw new CustomException("An unexpected error occurred. Please try again later.", e);
        }
    }

}
