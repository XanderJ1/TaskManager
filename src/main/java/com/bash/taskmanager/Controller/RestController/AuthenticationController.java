package com.bash.taskmanager.Controller.RestController;

import com.bash.taskmanager.Data.DTO.LoginResponseDTO;
import com.bash.taskmanager.Data.DTO.RegistrationDTO;
import com.bash.taskmanager.Data.Models.User;
import com.bash.taskmanager.Service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User registerUser(@RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body.getUsername(), body.getPassword(), body.getEmail());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        System.out.println(body.getUsername() + body.getPassword());
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
     }

}
