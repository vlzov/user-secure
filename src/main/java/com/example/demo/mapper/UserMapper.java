package com.example.demo.mapper;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .age(request.getAge())
                .build();
    }

    public void updateEntity(User user, UserRequest request) {
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .createdAt(user.getCreatedAt())
                .build();
    }

}