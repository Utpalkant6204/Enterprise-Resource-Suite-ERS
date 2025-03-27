package com.example.Enterprise.Resource.Suite.ERS.Services.Impl;

import com.example.Enterprise.Resource.Suite.ERS.Config.Configuration;
import com.example.Enterprise.Resource.Suite.ERS.Services.Interface.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final Configuration configuration;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, Configuration configuration) {
        this.javaMailSender = javaMailSender;
        this.configuration = configuration;
    }

    @Override
    public boolean sendPassWordResetOTP(String email, String otp) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(configuration.getHostEmail());
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("OTP For Reset Password");
            simpleMailMessage.setText("Your OTP for password reset is: " + otp +
                    "\nThis OTP will expire in 10 minutes." +
                    "\nDo not share this OTP with anyone.");

            javaMailSender.send(simpleMailMessage);
            log.info("SuccessFully Send OTP on email : {}", email);
            return true;
        }
        catch (Exception e){
            log.error("Error While Sending Otp {}", e);
            return false;
        }
    }
}
