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

    final PasswordEncoder passwordEncoder;

    final UserService userService;

    public UsersRestController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Endpoint to fetch all users
     * @return all users
     */
    @GetMapping("/users")
    List<UserDTO> fetchUsers(){
        return userService.fetchAll();
    }

    /**
     * Endpoint to fetch all tasks created by a user
     * @param id id of the user
     * @return tasks created by a user.
     */
    @GetMapping("/{id}/tasks")
    public Set<Task> UserTasks(@PathVariable Long id){
        return userService.fetchTasks(id);
    }

    /**
     * Endpoint to update user details
     * @param id id of the user to be updated.
     * @param email new email of the user.
     * @param userName new username of the user.
     */
    @PutMapping("/{id}/updateUserParameter")
    public void updateUser(@PathVariable Long id,
                            @RequestParam String email,
                            @RequestParam String userName){
        userService.updateUserParameter(id, email, userName);
    }

    /**
     * Endpoint to update user details.
     * @param id id of the user to be updated.
     * @param user new user object that contains the details to be updated.
     */
    @PutMapping("/{id}/updateUser")
    public void updateUser(@PathVariable Long id, @RequestBody User user){
        userService.updateUser(id, user);
    }
}
