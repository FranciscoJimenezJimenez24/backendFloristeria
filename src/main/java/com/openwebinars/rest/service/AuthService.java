package com.openwebinars.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openwebinars.rest.modelo.Usuario;
import com.openwebinars.rest.modelo.UsuarioRepositorio;

@Service
public class AuthService {
    /*@Autowired
    private UsuarioRepositorio usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean autenticar(String usuario, String contrasena) {
        Usuario usuarioEncontrado = usuarioRepository.findByUsuario(usuario);
        if (usuarioEncontrado != null) {
            return passwordEncoder.matches(contrasena, usuarioEncontrado.getContrasena());
        }
        return false;
    }*/
}

