package com.openwebinars.rest.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openwebinars.rest.modelo.Usuario;
import com.openwebinars.rest.modelo.UsuarioRepositorio;
import java.lang.Override;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
    private UsuarioRepositorio usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsuario(username);
        Usuario usuario = optionalUsuario.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return User.withUsername(usuario.getUsuario())
                   .password(passwordEncoder.encode(usuario.getContrasena()))
                   .roles(usuario.getRol())
                   .build();
    }

}
