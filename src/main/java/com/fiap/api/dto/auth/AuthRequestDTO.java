package com.fiap.api.dto.auth;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String username;
    private String password;
} 