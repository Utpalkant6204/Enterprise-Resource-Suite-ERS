package com.example.Enterprise.Resource.Suite.ERS.Services.Impl;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.LoginDTO;
import com.example.Enterprise.Resource.Suite.ERS.Entity.Employee;
import com.example.Enterprise.Resource.Suite.ERS.Repositories.EmployeeRepository;
import com.example.Enterprise.Resource.Suite.ERS.Services.Interface.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public AuthServiceImpl(JwtService jwtService, AuthenticationManager authenticationManager, EmployeeRepository employeeRepository) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public String validateUser(LoginDTO loginDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        Employee employee = employeeRepository.findByEmail(loginDTO.getEmail());
        List<String> roles = List.of(employee.getRole().name());

        return jwtService.generateToken(employee.getEmail(), roles);

    }
}
