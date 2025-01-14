package com.bash.taskmanager.Data.DTO;

import com.bash.taskmanager.Data.Models.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginResponseDTO {

    private Long id;

    private String username;

    private String email;

    private List<String> role = new ArrayList<>();

    private List<String> task = new ArrayList<>();

    private String jwt;

    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(String jwt){
        this.jwt = jwt;
    }
}
