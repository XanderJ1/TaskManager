package com.bash.taskmanager.Data.DTO;
import lombok.Data;

@Data
public class RegistrationDTO {
    private String username;
    private String email;
    private String password;
    private Roles role;

    RegistrationDTO(){
        super();
    }

    public enum Roles{
        ADMIN, USER
    }
}
