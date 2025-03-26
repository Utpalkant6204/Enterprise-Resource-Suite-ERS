package com.example.Enterprise.Resource.Suite.ERS.Controllers;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.LoginDTO;
import com.example.Enterprise.Resource.Suite.ERS.Services.Interface.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService loginService;

    @Autowired
    public AuthController(AuthService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO)
    {
        String token = loginService.validateUser(loginDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
