package com.backend.floristeria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.floristeria.dto.UserDTO;
import com.backend.floristeria.modelo.User;
import com.backend.floristeria.modelo.UserRepositorio;
import com.backend.floristeria.request.LoginRequest;
import com.backend.floristeria.request.RegisterRequest;
import com.backend.floristeria.request.UserRequest;
import com.backend.floristeria.response.AuthResponse;
import com.backend.floristeria.response.UserResponse;
import com.backend.floristeria.service.AuthService;
import com.backend.floristeria.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserController {
	
	private final UserRepositorio userRepositorio;
	private final UserService userService;
	private final AuthService authService;
	
	@GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepositorio.findAll();
		
		if (users.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(users);
		}
    }
	
	@GetMapping(value = "{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable int id){
		UserDTO userDTO = userService.getUser(id);
		if (userDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userDTO);
	}
	
	@PutMapping()
	public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest){
		switch (userRequest.getRol()) {
			case USER:
				return ResponseEntity.ok(userService.updateUser(userRequest));
			case WORKER:
				return ResponseEntity.ok(userService.updateUserWorker(userRequest));
			case ADMIN:
				return ResponseEntity.ok(userService.updateUserAdmin(userRequest));
		}
		return null;
		
	}
	
	@PostMapping()
    public ResponseEntity<User> crearUsuario(@RequestBody User nuevoUsuario) {
		RegisterRequest request = new RegisterRequest(nuevoUsuario.getUsername(),
														nuevoUsuario.getPassword(),
														nuevoUsuario.getFirstname(),
														nuevoUsuario.getLastname(),
														nuevoUsuario.getCountry());
		AuthResponse response = null;
		switch (nuevoUsuario.getRole()) {
			case ADMIN:
				response = authService.registerADMIN(request);
				break;
			case USER:
				response = authService.register(request);
				break;
			case WORKER:
				response = authService.registerWorker(request);
				break;
		}
		
        return ResponseEntity.status(201).body(nuevoUsuario);
    }
	
	@PostMapping("/login")
	public ResponseEntity<User> obtenerUsuarioLogin(@RequestBody LoginRequest loginRequest) {
	    String username = loginRequest.getUsername();
	    Optional<User> user = userService.obtenerUsuarioLogin(username);
	    if (user.isPresent()) {
	        return ResponseEntity.ok(user.get());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
}
