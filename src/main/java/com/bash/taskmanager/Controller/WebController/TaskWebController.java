package com.bash.taskmanager.Controller.WebController;

import com.bash.taskmanager.Data.DTO.RegistrationDTO;
import com.bash.taskmanager.Data.Models.Task;
import com.bash.taskmanager.Service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

@Slf4j
@RequestMapping("/")
@Controller
public class TaskWebController {

    @Autowired
    private final TaskService taskService;

    public TaskWebController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping()
    public String tasks(Model model){
        List<Task> tasks = taskService.fetchTasks();
        model.addAttribute("test", tasks);
        model.addAttribute("test2", tasks.get(2));
        return "TaskManager";
    }

    @GetMapping("/createTask")
    public String createTask(Model model){
         model.addAttribute("task", new Task());
        return "createTask";
    }

    public String createUser(@ModelAttribute("task") Task task, BindingResult result){
        if (result.hasErrors()){
            return "createTask";
        }
        taskService.addTask(task);
        return "redirect:/success";
    }
}
