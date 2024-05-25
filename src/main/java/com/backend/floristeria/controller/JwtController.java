package com.backend.floristeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.floristeria.jwt.JwtService;
import com.backend.floristeria.request.JwtRefreshRequest;
import com.backend.floristeria.response.JwtResponse;

@RestController
public class JwtController {

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody JwtRefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();
        
        // Validar el token de actualización
        if (jwtService.isRefreshTokenValid(refreshToken)) {
            String username = jwtService.getUsernameFromRefreshToken(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            // Generar un nuevo token
            String newToken = jwtService.generateRefreshToken(userDetails);
            
            // Devolver el nuevo token
            return ResponseEntity.ok(new JwtResponse(newToken));
        } else {
            // Manejar el caso de token de actualización inválido
            return ResponseEntity.badRequest().build();
        }
    }
}

