package com.bash.taskmanager.Service;

import com.bash.taskmanager.Data.DTO.RegistrationDTO;
import com.bash.taskmanager.Data.DTO.UserDTO;
import com.bash.taskmanager.Data.Models.Task;
import com.bash.taskmanager.Data.Models.User;
import com.bash.taskmanager.Data.Models.Role;
import com.bash.taskmanager.Repository.RoleRepository;
import com.bash.taskmanager.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bash.taskmanager.Data.DTO.UserDTOMapper;
import org.springframework.web.bind.annotation.RequestBody;

import javax.xml.crypto.Data;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
public class UserService implements UserDetailsService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDTOMapper userDTOMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("In user access level");
        return userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("Invalid User!"));

    }

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserDTO> fetchAll(){
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public Set<Task> fetchTasks(Long id) {
        return userRepository.findById(id).get().getTask();
    }

    public void addUser(RegistrationDTO registrationDTO) {
            Set<Role> roles = new HashSet<>();
            RegistrationDTO.Roles userRole;
            if (registrationDTO.getRole() != null){
                userRole = registrationDTO.getRole();
            }else {
                userRole = RegistrationDTO.Roles.USER;
            };
            Role role = roleRepository.findByAuthority(userRole.name())
                    .orElseGet(() -> roleRepository.save(new Role(userRole.name())));
            roles.add(role);
            String  password = passwordEncoder.encode(registrationDTO.getPassword());
            User user = new User(registrationDTO.getUsername(), password, registrationDTO.getEmail(), roles);
            userRepository.save(user);
    }

    public void updateUserParameter(Long id, String email, String username){
        User user = userRepository.findById(id).get();
        user.setEmail(email);
        user.setUsername(username);
        userRepository.save(user);
    }

    public void updateUser(Long id, User user){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User does not exist"));

        if (user.getUsername() != null)
            existingUser.setUsername(user.getUsername());
        if (user.getEmail() != null)
            existingUser.setEmail(user.getEmail());
        userRepository.save(existingUser);
    }

    public static void main(String[] args) {
        System.out.println();
    }
}