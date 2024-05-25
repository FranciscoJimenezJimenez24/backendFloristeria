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

import com.openwebinars.rest.modelo.Carrito;
import com.openwebinars.rest.modelo.CarritoRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CarritoController {
	
	private final CarritoRepositorio carritoRepositorio;
	
	@GetMapping("/carrito")
	public ResponseEntity<?> getCarritos(){
		List<Carrito> result=carritoRepositorio.findAll();
		
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(result);
		}
	}
	
	@GetMapping("/carrito/{id}")
	public ResponseEntity<?> obtenerUno(@PathVariable Long id) {
		Carrito result = carritoRepositorio.findById(id).orElse(null);
		if (result == null)
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(result);
	}
	
	@PostMapping("/carrito")
	public ResponseEntity<?> nuevoCarrito(@RequestBody Carrito nuevo) {
		Carrito saved = carritoRepositorio.save(nuevo);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@PutMapping("/carrito/{id}")
	public ResponseEntity<?> editarCarrito(@RequestBody Carrito editar, @PathVariable Long id) {
	    return carritoRepositorio.findById(id).map((Carrito carrito) -> {
	        carrito.setId_cliente(editar.getId_cliente());
	        carrito.setId_producto(editar.getId_producto());
	        return ResponseEntity.ok(carritoRepositorio.save(carrito));
	    }).orElseGet(() -> {
	        return ResponseEntity.notFound().build();
	    });
	}
	
	@DeleteMapping("/carrito/{id}")
	public ResponseEntity<?> borrarCarrito(@PathVariable Long id) {
		carritoRepositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
