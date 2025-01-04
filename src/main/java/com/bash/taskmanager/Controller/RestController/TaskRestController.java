package com.bash.taskmanager.Controller.RestController;

import com.bash.taskmanager.Data.Models.Task.Status;
import com.bash.taskmanager.Data.Models.Task;
import com.bash.taskmanager.Service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/tasks")
public class TaskRestController {
    @Autowired
    TaskService service;

    @GetMapping("/fetchTasks")
    List<Task> getAllTasks(){
        return service.fetchTasks();
    }

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

    @PostMapping("/add")
    void addTasks(@RequestBody Task task){
        service.addTask(task);
    }

    @PutMapping("/{id}/update")
    void updateTask(@PathVariable Long id,
                    @RequestParam(required = false) String  title,
                    @RequestParam(required = false) String description,
                    @RequestParam(required = false) String  date,
                    @RequestParam(required = false) Integer priority,
                    @RequestParam(required = false) Status status) {

        service.taskUpdate(id, title,description,date,priority,status);
    }

    @PostMapping("/addTask")
    public void createTask(@ModelAttribute Task task){
        Task newTask = new Task(
                task.getTitle(), task.getDescription(),
                task.getDueDate(), task.getPriority(), task.getStatus()
        );
        service.addTask(newTask);
    }

}
