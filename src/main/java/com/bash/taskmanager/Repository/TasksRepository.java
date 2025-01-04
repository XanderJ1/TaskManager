package com.bash.taskmanager.Repository;

import com.bash.taskmanager.Data.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksRepository extends JpaRepository<Task, Long> {
}
