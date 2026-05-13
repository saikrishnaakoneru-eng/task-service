package com.example.task_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.task_service.Task;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequest request) {
        try {
            com.example.task_service.Task task = taskService.createTask(request);
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<Task>> getUserTasks(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getUserTasks(userId));
    }

    // ADD THIS ↓
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getUserTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getUserTasks(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<com.example.task_service.Task>> getUserTasks(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getUserTasks(userId));
    }
}