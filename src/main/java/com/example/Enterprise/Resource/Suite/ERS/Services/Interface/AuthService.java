package com.example.Enterprise.Resource.Suite.ERS.Services.Interface;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.LoginDTO;

public interface AuthService {
    public String validateUser(LoginDTO loginDTO);
}
