package org.uz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthenticationService {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationService.class,args);
        System.out.print("Authentication service started---------");
    }
}