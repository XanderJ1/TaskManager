package com.bash.taskmanager.Data.DTO;

import com.bash.taskmanager.Data.Models.User;
import lombok.Data;

@Data
public class LoginResponseDTO {
    private User user;
    private String jwt;

    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(User user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }
}
