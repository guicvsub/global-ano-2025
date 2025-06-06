package com.fiap.api.controller;

import com.fiap.api.dto.auth.AuthRequestDTO;
import com.fiap.api.dto.auth.AuthResponseDTO;
import com.fiap.api.dto.auth.RefreshTokenRequestDTO;
import com.fiap.api.security.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtHelper jwtHelper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        System.out.println("Tentando autenticar: " + request.getUsername());
    
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
    
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("Usuário autenticado: " + userDetails.getUsername());
    
        String accessToken = jwtHelper.generateAccessToken(userDetails);
        String refreshToken = jwtHelper.generateRefreshToken(userDetails);
    
        System.out.println("Access Token: " + accessToken);
        System.out.println("Refresh Token: " + refreshToken);
    
        return ResponseEntity.ok(new AuthResponseDTO(
                accessToken,
                refreshToken,
                "Bearer",
                900 // 15 minutos em segundos
        ));
    }
    

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO request) {
        String username = jwtHelper.extractUsername(request.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtHelper.isTokenValid(request.getRefreshToken(), userDetails)) {
            String accessToken = jwtHelper.generateAccessToken(userDetails);
            String refreshToken = jwtHelper.generateRefreshToken(userDetails);

            return ResponseEntity.ok(new AuthResponseDTO(
                    accessToken,
                    refreshToken,
                    "Bearer",
                    900 // 15 minutos em segundos
            ));
        }

        return ResponseEntity.status(401).build();
    }
} 