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

	@Bean
	CommandLineRunner run(
			RoleRepository roleRepository,
			UserRepository userRepository,
			PasswordEncoder passwordEncoder){
		return (args -> {
			if (roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			 roleRepository.save(new Role("USER"));

			 Set<Role> roles = new HashSet<>();
			 roles.add(adminRole);

			User admin = new User("admin", passwordEncoder.encode("password"),roles);

			userRepository.save(admin);
		});
	}

}
