package com.bash.taskmanager.Data.DTO;
import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;

    LoginDTO(){
        super();
    }
}
