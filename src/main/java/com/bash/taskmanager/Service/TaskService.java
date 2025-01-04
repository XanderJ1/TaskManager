package com.bash.taskmanager.Service;

import com.bash.taskmanager.Data.Models.Task;
import com.bash.taskmanager.Repository.TasksRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

import static java.lang.Long.valueOf;

@Service
public class TaskService {

    @Autowired
    TasksRepository tasksRepository;


    public List<Task> fetchTasks(){
        return tasksRepository.findAll();
    }

    public String  fetchTitle(Long id) {
        return tasksRepository.findById(id).get().getTitle();
    }

    public LocalDate fetchDate(Long id){
        return tasksRepository.findById(id).get().getDueDate();
    }

    public void addTask(Task task) {
        tasksRepository.save(task);
    }

    @Transactional
    public void taskUpdate(Long id,
                           String title,
                           String description,
                           String date,
                           Integer priority,
                           Task.Status status) {
        Task task = tasksRepository.findById(id).get();
        if (!(title ==null))
            task.setTitle(title);
        if (!(description == null))
            task.setDescription(description);
        if (!(date == null))
            task.setDueDate(LocalDate.parse(date));
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
