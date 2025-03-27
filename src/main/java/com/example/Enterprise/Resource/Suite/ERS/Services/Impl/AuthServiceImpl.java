package com.example.Enterprise.Resource.Suite.ERS.Services.Impl;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.LoginDTO;
import com.example.Enterprise.Resource.Suite.ERS.Entity.Employee;
import com.example.Enterprise.Resource.Suite.ERS.Exceptions.CustomException;
import com.example.Enterprise.Resource.Suite.ERS.Repositories.EmployeeRepository;
import com.example.Enterprise.Resource.Suite.ERS.Services.Interface.AuthService;
import com.example.Enterprise.Resource.Suite.ERS.Services.Interface.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;

    @Autowired
    public AuthServiceImpl(JwtService jwtService, AuthenticationManager authenticationManager, EmployeeRepository employeeRepository, EmailService emailService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
    }

    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 10;

    @Override
    public String validateUser(LoginDTO loginDTO) {
        var Auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        if(Auth.isAuthenticated()) {
            Employee employee = employeeRepository.findByEmail(loginDTO.getEmail());
            List<String> roles = List.of(employee.getRole().name());

            return jwtService.generateToken(employee.getEmail(), roles);
        }
        else{
            log.error("Wrong UserName or Password : {}", loginDTO.getEmail());
            throw new CustomException("Wrong UserName or Password");
        }
    }

    @Override
    public boolean initiatePasswordReset(String email) {

        try {
            Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByEmail(email));

            if(employee.isEmpty()) {
                return false;
            }

            String otp = generateOtp();
            return emailService.sendPassWordResetOTP(employee.get().getEmail(), otp);
        }
        catch (Exception e) {
            log.error("Error While Sending otp to {}", email);
            throw new CustomException("Error While Sending otp to mail", e);
        }
    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}
