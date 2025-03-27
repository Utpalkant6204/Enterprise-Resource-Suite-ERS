package com.example.Enterprise.Resource.Suite.ERS.Config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Configuration {

    @Value("${spring.mail.username}")
    private String hostEmail;
}
