package com.bash.taskmanager.Data.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate dueDate;
    private Integer priority;

    @Enumerated(EnumType.STRING)
    private Status status = Status.IDLE; // Default value

    @ManyToOne(cascade = CascadeType.PERSIST)  // Cascade persist for assignedUser
    @JoinColumn(name = "user_id")
    private User assignedUser;

    public enum Status {
        IDLE, INITIATED, DONE
    }

    public Task() {}

    public Task(String title, String description, LocalDate dueDate, Integer priority, Status status) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    public Task(Long id, String title, String description, LocalDate dueDate, Integer priority, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }
}
