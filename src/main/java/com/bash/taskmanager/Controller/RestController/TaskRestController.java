package com.bash.taskmanager.Controller.RestController;

import com.bash.taskmanager.Data.DTO.TaskDTO;
import com.bash.taskmanager.Data.Models.Task.Status;
import com.bash.taskmanager.Data.Models.Task;
import com.bash.taskmanager.Service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller that contains all task endpoints and handles all task operations.
 */

@Slf4j
@RestController
@RequestMapping("api/tasks")
public class TaskRestController {

    final TaskService service;

    public TaskRestController(TaskService service){
        this.service = service;
    }

    /**
     * Endpoint to fetch all tasks created
     * @return All tasks created
     */
    @GetMapping("/fetchTasks")
    List<TaskDTO> getAllTasks(){
        return service.fetchTasks();
    }

    /**
     * Endpoint to fetch title of a task.
     * @param id id of the task to be fetched.
     * @return Task title
     */
    @GetMapping("{id}/fetchTitle")
    String fetchTitle(@PathVariable Long id){
        return service.fetchTitle(id);
    }

    @GetMapping("{id}/fetchDescription")
    String getDescription(@PathVariable Long id){
        return service.fetchDescription(id);
    }

    @GetMapping("{id}/fetchDate")
    LocalDate getDate(@PathVariable Long id){
        return service.fetchDate(id);
    }

    @GetMapping("{id}/fetchPriority")
    Integer getPriority(@PathVariable Long id){
        return service.fetchPriority(id);
    }

    @GetMapping("{id}/fetchStatus")
    Status getStatus(@PathVariable Long id){
        return service.fetchStatus(id);
    }

    /**
     * Endpoint to create a new task
     * @param task
     */
    @PostMapping("/add")
    void addTasks(@RequestBody Task task){
        service.addTask(task);
    }

    /**
     * Endpoint to update an existing task.
     * @param id id of the task to be updated
     * @param title new title for the task
     * @param description new description of the task.
     * @param priority new priority for the task.
     * @param status new/current status of the task.
     */
    @PutMapping("/{id}/update")
    void updateTask(@PathVariable Long id,
                    @RequestParam(required = false) String  title,
                    @RequestParam(required = false) String description,
                    @RequestParam(required = false) Integer priority,
                    @RequestParam(required = false) Status status) {

        service.taskUpdate(id, title,description, priority,status);
    }

    /**
     * Endpoint to create a task on the web.
     * @param task new task.
     */
    @PostMapping("/addTask")
    public void createTask(@ModelAttribute Task task){
        Task newTask = new Task(
                task.getTitle(), task.getDescription(),
                task.getDueDate(), task.getPriority(), task.getStatus()
        );
        service.addTask(newTask);
    }

}
