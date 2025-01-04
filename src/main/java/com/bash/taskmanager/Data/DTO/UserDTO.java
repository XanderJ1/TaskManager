package com.bash.taskmanager.Data.DTO;

import com.bash.taskmanager.Data.Models.Task;
import java.util.List;


public record UserDTO (
    Long id,
    String username,
    String email,
    List<String> role,
    List<String> task
){

}
