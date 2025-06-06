package com.fiap.api.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        String rawPassword = "123456";  // senha que você quer criptografar
        String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);
        System.out.println(encodedPassword);
    }
}
