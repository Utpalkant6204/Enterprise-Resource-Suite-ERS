package com.example.Enterprise.Resource.Suite.ERS.Services.Interface;

public interface EmailService {
    boolean sendPassWordResetOTP(String email, String otp);
}
