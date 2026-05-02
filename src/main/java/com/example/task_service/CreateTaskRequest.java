package com.example.task_service;

import lombok.Data;

@Data
public class CreateTaskRequest {
    private String title;
    private String description;
    private Long userId;
}
