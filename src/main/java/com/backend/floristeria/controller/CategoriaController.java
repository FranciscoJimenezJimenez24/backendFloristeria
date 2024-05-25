package com.backend.floristeria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.backend.floristeria.modelo.Categoria;
import com.backend.floristeria.modelo.CategoriaRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = {"http://localhost:4200"})
public class CategoriaController {
	
	private final CategoriaRepositorio categoriaRepositorio;
	
	@GetMapping("/categoria")
	public ResponseEntity<?> getCategorias(){
		List<Categoria> result=categoriaRepositorio.findAll();
		
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(result);
		}
	}
	
	
	@GetMapping("/categoria/{id}")
	public ResponseEntity<?> obtenerUno(@PathVariable Long id) {
		Categoria result = categoriaRepositorio.findById(id).orElse(null);
		if (result == null)
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(result);
	}
	
	@PostMapping("/categoria")
	public ResponseEntity<?> nuevoCategoria(@RequestBody Categoria nuevo) {
		Categoria saved = categoriaRepositorio.save(nuevo);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@PutMapping("/categoria/{id}")
	public ResponseEntity<?> editarCategoria(@RequestBody Categoria editar, @PathVariable Long id) {
	    return categoriaRepositorio.findById(id).map((Categoria categoria) -> {
	        categoria.setNombre(editar.getNombre());
	        return ResponseEntity.ok(categoriaRepositorio.save(categoria));
	    }).orElseGet(() -> {
	        return ResponseEntity.notFound().build();
	    });
	}
	
	@DeleteMapping("/categoria/{id}")
	public ResponseEntity<?> borrarCategoria(@PathVariable Long id) {
		categoriaRepositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
