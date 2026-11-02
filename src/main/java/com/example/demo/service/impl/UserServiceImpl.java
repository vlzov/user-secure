package com.example.demo.service.impl;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.ApiException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getById(Long id) {
        User user = findEntity(id);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse create(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException(ErrorCode.USER_EMAIL_TAKEN)
                    .addDetail("email", request.getEmail());
        }

        User user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        User user = findEntity(id);

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException(ErrorCode.USER_EMAIL_TAKEN)
                    .addDetail("email", request.getEmail());
        }

        userMapper.updateEntity(user, request);
        return userMapper.toResponse(user); // managed entity -> flush on tx commit
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        User user = findEntity(id);
        userRepository.delete(user);
    }

    @Override
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND)
                        .addDetail("email", email));
        return userMapper.toResponse(user);
    }

    private User findEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND)
                        .addDetail("id", id));
    }

}