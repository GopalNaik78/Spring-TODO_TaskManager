package com.scaler.taskmanager.services;

import com.scaler.taskmanager.entities.TaskEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TaskService {
    private ArrayList<TaskEntity> tasks= new ArrayList<TaskEntity>();
    private int taskId=1;
    
    public TaskEntity addTask(String title, String description, String deadline){
        TaskEntity task=new TaskEntity();
        task.setId(taskId);
        task.setTitle(title);
        task.setDescription(description);
        //task.setDeadline(new Date(deadline)); //TODO: validate date format YYYY-MM-DD
        task.setCompleted(false);
        tasks.add(task);
        taskId++;
        return task;
    }

    public ArrayList<TaskEntity> getTasks(){
        return tasks;
    }

    public TaskEntity getTaskById(int id){
        //tasks.stream().findAny().filter(task -> task.getId()==id).orElse(null);
        for(TaskEntity task:tasks){
            if(task.getId()==id){
                return task;
            }
        }
        return null;
    }
}
