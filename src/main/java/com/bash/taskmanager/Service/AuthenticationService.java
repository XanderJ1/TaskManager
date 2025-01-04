package com.bash.taskmanager.Service;

import com.bash.taskmanager.Data.DTO.LoginResponseDTO;
import com.bash.taskmanager.Data.Models.Role;
import com.bash.taskmanager.Data.Models.User;
import com.bash.taskmanager.Repository.RoleRepository;
import com.bash.taskmanager.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    public AuthenticationService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;

    }
    public User registerUser(String username, String password, String email){
        Role role = roleRepository.findByAuthority("USER").get();
        String encodedPassword = passwordEncoder.encode(password);
        Set<Role> authorities = new HashSet<>();
        authorities.add(role);
        return userRepository.save(new User(username, encodedPassword, email, authorities));
    }

    public LoginResponseDTO loginUser(String username, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);
            System.out.println(token);
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);
        }catch (AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }
}