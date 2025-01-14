package com.bash.taskmanager.Service;

import com.bash.taskmanager.Data.DTO.TaskDTO;
import com.bash.taskmanager.Data.Models.Task;
import com.bash.taskmanager.Data.Models.User;
import com.bash.taskmanager.Repository.TasksRepository;
import com.bash.taskmanager.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.lang.Long.valueOf;

@Service

public class TaskService {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TasksRepository tasksRepository;


    public List<TaskDTO> fetchTasks(){
        List<Task> tasks = tasksRepository.findAll();
        List<TaskDTO> taskDTOList = new LinkedList<>();
        for (Task task : tasks) {
            TaskDTO taskDTO = new TaskDTO();
            if (task.getTitle() != null)
                taskDTO.setTitle(task.getTitle());
            if (task.getTitle() != null)
                taskDTO.setDescription(task.getDescription());
            if (task.getPriority() != null)
                taskDTO.setPriority(task.getPriority());
            if (task.getDueDate() != null)
                taskDTO.setDueDate(task.getDueDate().toString());
            if (task.getStatus() != null)
                taskDTO.setStatus(task.getStatus().toString());
            if (task.getAssignedUser() != null) {
                taskDTO.setAssignedUser(task.getAssignedUser().getUsername());
            }else
                taskDTO.setAssignedUser("Preload");
            taskDTOList.add(taskDTO);
        }
        return taskDTOList;
    }

    public String  fetchTitle(Long id) {
        return tasksRepository.findById(id).get().getTitle();
    }

    public LocalDate fetchDate(Long id){
        return tasksRepository.findById(id).get().getDueDate();
    }

    public String addTask(Task task) {
        String username = authenticationService.getAuthenticatedUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid user"));
        task.setAssignedUser(user);
        user.getTask().add(task);
        tasksRepository.save(task);
        return "TaskManager";
    }

    @Transactional
    public void taskUpdate(Long id,
                           String title,
                           String description,
                           Integer priority,
                           Task.Status status) {
        Task task = tasksRepository.findById(id).get();
        if (!(title ==null))
            task.setTitle(title);
        if (!(description == null))
            task.setDescription(description);
        if (!(priority == null))
            task.setPriority(priority);
        if (!(status ==null))
            task.setStatus(status);
    }

    public Integer fetchPriority(Long id) {
       return tasksRepository.findById(id).get().getPriority();
    }

    public Task.Status fetchStatus(Long id) {
        return tasksRepository.findById(id).get().getStatus();
    }

    public String fetchDescription(Long id) {
        return tasksRepository.findById(id).get().getDescription();
    }

}
