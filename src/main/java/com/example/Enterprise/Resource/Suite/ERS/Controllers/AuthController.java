package com.example.Enterprise.Resource.Suite.ERS.Controllers;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.CriteriaDTO;
import com.example.Enterprise.Resource.Suite.ERS.DTOS.LoginDTO;
import com.example.Enterprise.Resource.Suite.ERS.DTOS.SearchDTO;
import com.example.Enterprise.Resource.Suite.ERS.Services.Interface.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService loginService;

    @Autowired
    public AuthController(AuthService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/v1/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO)
    {
        String token = loginService.validateUser(loginDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/v1/send-otp")
    public ResponseEntity<?> otpRequestForResetPassword(@RequestBody SearchDTO searchDTO) {
        boolean initiate = loginService.initiatePasswordReset(searchDTO.getCriteriaDTO().getSearchText());

        return initiate
                ? new ResponseEntity<>("OTP Send Over Mail", HttpStatus.OK)
                : new ResponseEntity<>("User Not Found", HttpStatus.BAD_REQUEST);
    }
}
