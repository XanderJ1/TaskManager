package com.bash.taskmanager.Data.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(exclude = {"task"})
@Entity
@Table(name = "appUser")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id;

    @Column(nullable = false, unique = true )
    @NotBlank(message = "Username is required")
    String username;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    String password;

    String email;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(
            name = "connect",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> role;
    @OneToMany
    private Set<Task> task = new HashSet<>();

    public User(){

    }

    public User(String  username,String password, Set<Role> role ){
       this.id = id;
       this.username = username;
       this.password = password;
       this.role = role;
    }

    public User(
                String username,
                String password,
                String email,
                Set<Role> role,
                Set<Task> task) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.task = task;
    }

    public User(
            String username,
            String password,
            String email,
            Set<Role> role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(Long id,
                String username,
                String password,
                String email,
                Set<Role> role,
                Set<Task> task) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.task = task;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
