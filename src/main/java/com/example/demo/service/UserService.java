package com.example.demo.service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();
    UserResponse getById(Long id);
    UserResponse create(UserRequest request);
    UserResponse update(Long id, UserRequest request);
    void delete(Long id);
    UserResponse findByEmail(String email);
}