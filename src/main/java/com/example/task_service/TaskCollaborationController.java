package com.example.task_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TaskCollaborationController {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Receives task updates from clients via WebSocket
     * Broadcasts changes to ALL subscribed clients in real-time
     */
    @MessageMapping("/edit-task")
    @SendTo("/topic/task-updates")
    public TaskUpdate handleTaskEdit(TaskUpdate update) {
        try {
            // Save the change to database
            Task task = taskRepository.findById(update.getTaskId()).orElse(null);
            if (task != null) {
                if ("title".equals(update.getField())) {
                    task.setTitle(update.getValue());
                } else if ("description".equals(update.getField())) {
                    task.setDescription(update.getValue());
                } else if ("status".equals(update.getField())) {
                    task.setStatus(update.getValue());
                }
                taskRepository.save(task);
            }
        } catch (Exception e) {
            System.err.println("Error processing update: " + e.getMessage());
        }

        // Broadcast the update to all connected clients
        return update;
    }

    /**
     * Data class for task update messages
     */
    public static class TaskUpdate {
        private Long taskId;
        private String field;
        private String value;
        private String userId;
        private long timestamp;

        public TaskUpdate() {
            this.timestamp = System.currentTimeMillis();
        }

        public Long getTaskId() { return taskId; }
        public void setTaskId(Long taskId) { this.taskId = taskId; }

        public String getField() { return field; }
        public void setField(String field) { this.field = field; }

        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }

        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    }
}