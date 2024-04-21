package com.scaler.taskmanager.controllers;

import com.scaler.taskmanager.dtos.CreateTaskDTO;
import com.scaler.taskmanager.dtos.ErrorResponseDTO;
import com.scaler.taskmanager.dtos.TaskResponceDTO;
import com.scaler.taskmanager.dtos.UpdateTaskDTO;
import com.scaler.taskmanager.entities.TaskEntity;
import com.scaler.taskmanager.services.NotesService;
import com.scaler.taskmanager.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final NotesService notesService;
    private ModelMapper modelMapper = new ModelMapper();

    public TaskController(TaskService taskService, NotesService notesService) {
        this.taskService = taskService;
        this.notesService = notesService;
    }

    @GetMapping("")
    public ResponseEntity<List<TaskEntity>> getTasks() {
        var tasks = taskService.getTasks();

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponceDTO> getTaskById(@PathVariable("id") Integer id) {
        var task= taskService.getTaskById(id);
        var notes = notesService.getNotesForTask(id);
        if(task == null)
            return ResponseEntity.notFound().build();

        var taskResponse = modelMapper.map(task, TaskResponceDTO.class);
        taskResponse.setNotes(notes);
        return ResponseEntity.ok(taskResponse);
    }

    @PostMapping("")
    public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDTO body) throws ParseException {
        var task=taskService.addTask(body.getTitle(),body.getDescription(),body.getDeadline());
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") Integer id, @RequestBody UpdateTaskDTO body) throws ParseException {
        var task = taskService.updateTask(id,body.getDescription(),body.getDeadline(),body.getCompleted());

        if (task == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(task);
    }


    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ErrorResponseDTO> handleErrors(Exception e){
        if(e instanceof ParseException){
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid date Format"));
        }
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Internal Server Error"));
    }
}
