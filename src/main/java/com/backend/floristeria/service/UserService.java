package com.backend.floristeria.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.floristeria.dto.UserDTO;
import com.backend.floristeria.modelo.Rol;
import com.backend.floristeria.modelo.User;
import com.backend.floristeria.modelo.UserRepositorio;
import com.backend.floristeria.request.UserRequest;
import com.backend.floristeria.response.UserResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositorio userRepository; 

    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {
       
        User user = User.builder()
        .id(userRequest.getId())
        .firstname(userRequest.getFirstname())
        .lastname(userRequest.getUsername())
        .country(userRequest.getCountry())
        .role(Rol.USER)
        .build();
        
        userRepository.updateUser(user.getId(), user.getFirstname(), user.getLastname(), user.getCountry(), user.getRole());

        return new UserResponse("El usuario se registró satisfactoriamente");
    }
    
    @Transactional
    public UserResponse updateUserWorker(UserRequest userRequest) {
       
        User user = User.builder()
        .id(userRequest.getId())
        .firstname(userRequest.getFirstname())
        .lastname(userRequest.getUsername())
        .country(userRequest.getCountry())
        .role(Rol.WORKER)
        .build();
        
        userRepository.updateUser(user.getId(), user.getFirstname(), user.getLastname(), user.getCountry(), user.getRole());

        return new UserResponse("El usuario se registró satisfactoriamente");
    }
    
    @Transactional
    public UserResponse updateUserAdmin(UserRequest userRequest) {
       
        User user = User.builder()
        .id(userRequest.getId())
        .firstname(userRequest.getFirstname())
        .lastname(userRequest.getUsername())
        .country(userRequest.getCountry())
        .role(Rol.ADMIN)
        .build();
        
        userRepository.updateUser(user.getId(), user.getFirstname(), user.getLastname(), user.getCountry(), user.getRole());

        return new UserResponse("El usuario se registró satisfactoriamente");
    }
    
    

    public UserDTO getUser(Integer id) {
        User user= userRepository.findById(id).orElse(null);
       
        if (user!=null)
        {
            UserDTO userDTO = UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .country(user.getCountry())
            .build();
            return userDTO;
        }
        return null;
    }
    
    public Optional<User> obtenerUsuarioLogin(String usuario) {
    	Optional<User> user = userRepository.findByUsername(usuario);
        return user;
    }
    
    public boolean deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
        	userRepository.delete(user.get());
            return true;
        }
        return false;
    }
}
