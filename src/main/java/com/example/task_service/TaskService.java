package com.example.task_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public com.example.task_service.Task createTask(CreateTaskRequest request) throws Exception {
        try {
            // Call user-service via public IP to verify user exists
            String userServiceUrl = "http://18.118.209.91:8080/api/users/" + request.getUserId();
            ResponseEntity<String> response = restTemplate.getForEntity(userServiceUrl, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                com.example.task_service.Task task = new com.example.task_service.Task();
                task.setTitle(request.getTitle());
                task.setDescription(request.getDescription());
                task.setUserId(request.getUserId());
                task.setStatus("PENDING");
                return taskRepository.save(task);
            }
        } catch (Exception e) {
            throw new Exception("Error: User not found");
        }

        throw new Exception("Error: User not found");
    }

    public List<com.example.task_service.Task> getUserTasks(Long userId) {
        return taskRepository.findByUserId(userId);
    }
}