package com.backend.floristeria.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.floristeria.jwt.JwtService;
import com.backend.floristeria.modelo.Rol;
import com.backend.floristeria.modelo.User;
import com.backend.floristeria.modelo.UserRepositorio;
import com.backend.floristeria.request.LoginRequest;
import com.backend.floristeria.request.RegisterRequest;
import com.backend.floristeria.response.AuthResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepositorio userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    
    public AuthResponse login(LoginRequest request) {
    	try {
	        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
	        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
	        
	        // Generar un nuevo token de acceso
	        String token = jwtService.getToken(user);
	        
	        // Generar un nuevo token de actualización
	        String refreshToken = jwtService.generateRefreshToken(user);
	        
	        // Devolver el nuevo token de acceso y token de actualización en la respuesta
	        return AuthResponse.builder()
	                .token(token)
	                .refreshToken(refreshToken)
	                .build();
	    } catch (Exception e) {
	        // Manejar cualquier error de autenticación aquí
	        throw new RuntimeException("Error de autenticación: " + e.getMessage());
	    }
    }


    public AuthResponse register(RegisterRequest request) {
	    User user = User.builder()
	        .username(request.getUsername())
	        .password(passwordEncoder.encode(request.getPassword()))
	        .firstname(request.getFirstname()) 
	        .lastname(request.getLastname())
	        .country(request.getCountry())
	        .role(Rol.USER)
	        .build();
	    userRepository.save(user);
	
	    return AuthResponse.builder()
	            .token(jwtService.getToken(user))
	            .build();
	}
    
    public AuthResponse registerWorker(RegisterRequest request) {
	    User user = User.builder()
	        .username(request.getUsername())
	        .password(passwordEncoder.encode(request.getPassword()))
	        .firstname(request.getFirstname()) 
	        .lastname(request.getLastname())
	        .country(request.getCountry())
	        .role(Rol.WORKER)
	        .build();
	    userRepository.save(user);
	
	    return AuthResponse.builder()
	            .token(jwtService.getToken(user))
	            .build();
	}
    
    public AuthResponse registerADMIN(RegisterRequest request) {
	    User user = User.builder()
	        .username(request.getUsername())
	        .password(passwordEncoder.encode(request.getPassword()))
	        .firstname(request.getFirstname()) 
	        .lastname(request.getLastname())
	        .country(request.getCountry())
	        .role(Rol.ADMIN)
	        .build();
	    userRepository.save(user);
	
	    return AuthResponse.builder()
	            .token(jwtService.getToken(user))
	            .build();
	}
}
