package com.bash.taskmanager.TaskManagerConfig;

import com.bash.taskmanager.Data.Models.Task;
import com.bash.taskmanager.Repository.TasksRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static com.bash.taskmanager.Data.Models.Task.Status.DONE;
import static com.bash.taskmanager.Data.Models.Task.Status.INITIATED;

/**
 * Preloads tasks in the database
 */
@Configuration
public class TaskConfig {

    TasksRepository tasksRepository;

    public TaskConfig(TasksRepository tasksRepository){
        this.tasksRepository = tasksRepository;
    }

    @Bean
    public CommandLineRunner taskCommandLineRunner(){
        return args -> {
            Task task = new Task(
                    "Java",
                    "doodle",
                    LocalDate.now(),
                    3,
                    Task.Status.IDLE);
            Task task2 = new Task(
                    "Ai",
                    "Machine Learning",
                    LocalDate.now(),
                    0,
                    INITIATED);
            Task task3 = new Task(
                    "Huawei",
                    "Bingo",
                    LocalDate.now(),
                    2,
                    DONE);
            Task task4 = new Task(
                    "Kali",
                    "Jumbo",
                    LocalDate.now(),
                    4,
                    DONE);
            tasksRepository.saveAll(List.of(task,task2,task3,task4));
        };

    }
}
