package com.bash.taskmanager.Controller.WebController;

import com.bash.taskmanager.Data.DTO.RegistrationDTO;
import com.bash.taskmanager.Data.DTO.TaskDTO;
import com.bash.taskmanager.Data.Models.Task;
import com.bash.taskmanager.Service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller that contains all task endpoints for handling tasks on the web
 */
@Slf4j
@RequestMapping("/tasks")
@Controller
public class TaskWebController {

    @Autowired
    private final TaskService taskService;

    public TaskWebController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("")
    public String tasks(Model model){
        List<TaskDTO> tasks = taskService.fetchTasks();
        model.addAttribute("test", tasks);
        model.addAttribute("test2", tasks.get(2));
        return "tasks";
    }

    @GetMapping("/createTask")
    public String createTask(Model model){
        model.addAttribute("task", new Task());
        return "createTask";
    }

    @PostMapping("/createTask")
    public String TaskCreated(@ModelAttribute Task task){
        taskService.addTask(task);
        return "createTask";
    }

    /**
     * Endpoint to display all idle tasks.
     * @param model model to hold data to be passed to the web view.
     * @return all tasks with status, "IDLE"
     */
    @GetMapping("/idle")
    public String idle(Model model){
        List<TaskDTO> tasks = taskService.fetchTasks();
        List<TaskDTO> idleTasks = new ArrayList<>();
        if (!tasks.isEmpty()){
            for(TaskDTO task : tasks){
                if (task.getStatus().equals("IDLE"))
                    idleTasks.add(task);
            }
        }
        boolean isEmpty = idleTasks.isEmpty();
        model.addAttribute("show", isEmpty);
        model.addAttribute("test", idleTasks);
        model.addAttribute("test2", tasks.get(2));
        return "Idle";
    }

    /**
     * Endpoint to display all pending tasks
     * @param model model to hold data to be passed to the web view.
     * @return all task with status, "PENDING"
     */
    @GetMapping("/pending")
    public String pending(Model model){
        List<TaskDTO> tasks = taskService.fetchTasks();
        List<TaskDTO> pendingTasks = new ArrayList<>();
        if (!tasks.isEmpty()){
            for(TaskDTO task : tasks){
                if (task.getStatus().equals("PENDING"))
                    pendingTasks.add(task);
            }
        }
        boolean isEmpty = pendingTasks.isEmpty();
        model.addAttribute("show", isEmpty);
        model.addAttribute("test", pendingTasks);
        model.addAttribute("test2", tasks.get(2));
        return "Pending";
    }

    /**
     * Endpoint to display all done tasks
     * @param model model to hold data to be passed to the web view.
     * @return all task with status, "DONE"
     */
    @GetMapping("/done")
    public String done(Model model){
        List<TaskDTO> tasks = taskService.fetchTasks();
        List<TaskDTO> doneTasks = new ArrayList<>();
        if (!tasks.isEmpty()){
            for(TaskDTO task : tasks){
                if (task.getStatus().equals("IDLE"))
                    doneTasks.add(task);
            }
        }
        boolean isEmpty = doneTasks.isEmpty();
        model.addAttribute("show", isEmpty);
        model.addAttribute("test", doneTasks);
        model.addAttribute("test2", tasks.get(2));
        return "Done";
    }

}
