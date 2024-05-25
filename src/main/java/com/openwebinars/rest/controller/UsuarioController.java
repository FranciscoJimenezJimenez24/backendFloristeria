package com.openwebinars.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openwebinars.rest.modelo.Usuario;
import com.openwebinars.rest.modelo.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UsuarioController {
	
	private final UsuarioRepositorio usuarioRepositorio;
	
	@GetMapping("/usuario")
	public ResponseEntity<?> getUsuarios(){
		List<Usuario> result=usuarioRepositorio.findAll();
		
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(result);
		}
	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> obtenerUno(@PathVariable Long id) {
		Usuario result = usuarioRepositorio.findById(id).orElse(null);
		if (result == null)
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(result);
	}
	
	@PostMapping("/usuario")
	public ResponseEntity<?> nuevoUsuario(@RequestBody Usuario nuevo) {
		Usuario saved = usuarioRepositorio.save(nuevo);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@PutMapping("/usuario/{id}")
	public ResponseEntity<?> editarUsuario(@RequestBody Usuario editar, @PathVariable Long id) {
	    return usuarioRepositorio.findById(id).map((Usuario usuario) -> {
	        usuario.setUsuario(editar.getUsuario());
	        usuario.setContrasena(editar.getContrasena());
	        usuario.setRol(editar.getRol());
	        return ResponseEntity.ok(usuarioRepositorio.save(usuario));
	    }).orElseGet(() -> {
	        return ResponseEntity.notFound().build();
	    });
	}
	
	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<?> borrarUsuario(@PathVariable Long id) {
		usuarioRepositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
