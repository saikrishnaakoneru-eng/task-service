
package com.example.task_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.Data;

@Component
public class UserServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.service.url:http://user-service:8080}")
    private String userServiceUrl;

    public UserDto getUserById(Long userId) {
        try {
            String url = userServiceUrl + "/api/users/" + userId;  // ← USE THE VARIABLE!
            return restTemplate.getForObject(url, UserDto.class);
        } catch (Exception e) { return null; }


    }

    @Data
    public static class UserDto {
        public Long id;
        public String name;
        public String email;
    }
}