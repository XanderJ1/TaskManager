package com.bash.taskmanager.Data.DTO;

import com.bash.taskmanager.Data.Models.Task;
import com.bash.taskmanager.Data.Models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import java.util.stream.Collectors;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()),
                user.getTask()
                        .stream()
                        .map(Task::getTitle)
                        .collect(Collectors.toList())
        );
    }
}
