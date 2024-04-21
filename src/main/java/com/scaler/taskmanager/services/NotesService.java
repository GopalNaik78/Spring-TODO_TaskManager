package com.scaler.taskmanager.services;

import com.scaler.taskmanager.entities.NoteEntity;
import com.scaler.taskmanager.entities.TaskEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NotesService {

    private TaskService taskService;
    private HashMap<Integer, TaskNotesHolder> taskNotesHolders = new HashMap<>();

    public NotesService(TaskService taskService) {
        this.taskService = taskService;
    }


    class TaskNotesHolder{
        protected int noteId = 1;
        protected ArrayList<NoteEntity> notes =new ArrayList<>();
    }

    public List<NoteEntity> getNotesForTask(int taskId){
        TaskEntity task = taskService.getTaskById(taskId);
        if(task != null){
            return null;
        }
        if(taskNotesHolders.get(taskId) == null){
            taskNotesHolders.put(taskId, new TaskNotesHolder());
        }
        return taskNotesHolders.get(taskId).notes;
    }

    public NoteEntity addNoteForTask(int taskId, String title, String body){
        TaskEntity task = taskService.getTaskById(taskId);
        if(task != null){
            return null;
        }
        if(taskNotesHolders.get(taskId) == null){
            taskNotesHolders.put(taskId, new TaskNotesHolder());
        }
        TaskNotesHolder taskNotesHolder = taskNotesHolders.get(taskId);
        NoteEntity note = new NoteEntity();
        note.setId(taskNotesHolder.noteId);
        note.setTitle(title);
        note.setBody(body);
        taskNotesHolder.notes.add(note);
        taskNotesHolder.noteId++;
        return note;
    }
}
