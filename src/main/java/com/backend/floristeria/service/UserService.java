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
        
        userRepository.updateUser(user.getId(), user.getFirstname(), user.getLastname(), user.getCountry());

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
}
