package com.bash.taskmanager;

import com.bash.taskmanager.Data.Models.Role;
import com.bash.taskmanager.Data.Models.User;
import com.bash.taskmanager.Repository.RoleRepository;
import com.bash.taskmanager.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class TaskManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}


	}


