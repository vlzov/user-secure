package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message = "name must not be blank")
    @Size(max = 100, message = "name length must be <= 100")
    private String name;

    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be valid")
    @Size(max = 150, message = "email length must be <= 150")
    private String email;

    @NotNull(message = "age must not be null")
    @Min(value = 18, message = "age must be >= 18")
    @Max(value = 120, message = "age must be <= 120")
    private Integer age;

    @NotBlank(message = "password must not be blank")
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;

}