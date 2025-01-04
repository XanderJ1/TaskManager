package com.bash.taskmanager.Controller.RestController;

import com.bash.taskmanager.Data.DTO.RegistrationDTO;
import com.bash.taskmanager.Data.DTO.UserDTO;
import com.bash.taskmanager.Data.Models.Task;
import com.bash.taskmanager.Data.Models.User;
import com.bash.taskmanager.Data.Models.Role;
import com.bash.taskmanager.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RequestMapping("/api/users")
@RestController
public class UsersRestController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

    public UsersRestController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    List<UserDTO> fetchUsers(){
        return userService.fetchAll();
    }

    @GetMapping("/{id}/tasks")
    public Set<Task> UserTasks(@PathVariable Long id){
        return userService.fetchTasks(id);
    }

    @PutMapping("/{id}/updateUserParameter")
    public void updateEmail(@RequestParam Long id,
                            @RequestParam String email,
                            @RequestParam String userName){
        userService.updateUserParameter(id, email, userName);
    }

    @PutMapping("/{id}/updateUser")
    public void updateUser(@PathVariable Long id, @RequestBody User user){
        userService.updateUser(id, user);
    }
}
