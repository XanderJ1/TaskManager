package com.bash.taskmanager.Service;

import com.bash.taskmanager.Data.DTO.LoginResponseDTO;
import com.bash.taskmanager.Data.Models.Role;
import com.bash.taskmanager.Data.Models.Task;
import com.bash.taskmanager.Data.Models.User;
import com.bash.taskmanager.Repository.RoleRepository;
import com.bash.taskmanager.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@Slf4j
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
    @Autowired
    private UserService userService;

    /**
     *
     * @param authenticationManager
     * @param passwordEncoder
     */
    public AuthenticationService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;

    }

    /**
     * Fetches the username of a user who has logged in.
     * @return username of the user who is logged in at the time.
     */
    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("User is not authenticated");
        }
        return authentication.getName();
    }

    /**
     * Registers new users with the data provided.
     * @param username user's username
     * @param password user's password
     * @param email user's email
     * @return User object
     */
    public User registerUser(String username, String password, String email){
        if (roleRepository.findByAuthority("USER").isEmpty())
            roleRepository.save(new Role("USER"));
        if (roleRepository.findByAuthority("ADMIN").isEmpty())
            roleRepository.save(new Role("ADMIN"));
        Role role = roleRepository.findByAuthority("USER").get();
        String encodedPassword = passwordEncoder.encode(password);
        Set<Role> authorities = new HashSet<>();
        authorities.add(role);
        return userRepository.save(new User(username, encodedPassword, email, authorities));
    }

    /**
     * Logs a user in with data provided
     * @param username user's username
     * @param password user's password
     * @return LoginResponse DTO
     * @throws BadCredentialsException Invalid username
     */
    public LoginResponseDTO loginUser(String username, String password){

        try{
//            Authentication auth = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );

            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username : "+username));

            // Check if the user is found
            if (user == null) {
                log.info("User not found with username {}",username);
                throw new BadCredentialsException("Invalid username");
            }

            log.info("User Role = {}", user.getRole());
            log.info("Authenticating user: {}", username);

            // Check password match
            if (!passwordEncoder.matches(password, user.getPassword())) {
                log.error("Password mismatch for user: {}", username);
                throw new BadCredentialsException("Invalid email or password");
            }


            String token = tokenService.generateJwt(user);
            System.out.println(token);
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setId(user.getId());
            loginResponseDTO.setUsername(user.getUsername());
            loginResponseDTO.setEmail(user.getEmail());
            for (Role role : user.getRole()){
                loginResponseDTO.getRole().add(role.getAuthority());
            }
            for (Task task : user.getTask())
                loginResponseDTO.getTask().add(task.getTitle());
            loginResponseDTO.setJwt(token);
            return loginResponseDTO;
        }catch (AuthenticationException e){
            System.out.println("Invalid user");
        }
        return null;
    }
}