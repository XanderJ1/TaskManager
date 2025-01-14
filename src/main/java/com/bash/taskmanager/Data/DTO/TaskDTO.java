package com.bash.taskmanager.Data.DTO;

import lombok.Data;

@Data
public class TaskDTO {

    private String title;
    private String description;
    private String dueDate;
    private Integer priority;
    private String status;
    private String assignedUser;
}
