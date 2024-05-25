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

import com.openwebinars.rest.modelo.Cliente;
import com.openwebinars.rest.modelo.ClienteRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ClienteController {
	
	private final ClienteRepositorio clienteRepositorio;
	
	@GetMapping("/cliente")
	public ResponseEntity<?> getClientes(){
		List<Cliente> result=clienteRepositorio.findAll();
		
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(result);
		}
	}
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> obtenerUno(@PathVariable Long id) {
		Cliente result = clienteRepositorio.findById(id).orElse(null);
		if (result == null)
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(result);
	}
	
	@PostMapping("/cliente")
	public ResponseEntity<?> nuevoCliente(@RequestBody Cliente nuevo) {
		Cliente saved = clienteRepositorio.save(nuevo);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@PutMapping("/cliente/{id}")
	public ResponseEntity<?> editarCliente(@RequestBody Cliente editar, @PathVariable Long id) {
	    return clienteRepositorio.findById(id).map((Cliente cliente) -> {
	        cliente.setNombre(cliente.getNombre());
	        cliente.setApellidos(cliente.getApellidos());
	        cliente.setCiudad(cliente.getCiudad());
	        cliente.setCp(cliente.getCp());
	        cliente.setDireccion(cliente.getDireccion());
	        cliente.setEmail(cliente.getEmail());
	        cliente.setForma_pago(cliente.getForma_pago());
	        cliente.setId_usuario(cliente.getId_usuario());
	        cliente.setTelefono(cliente.getTelefono());
	        return ResponseEntity.ok(clienteRepositorio.save(cliente));
	    }).orElseGet(() -> {
	        return ResponseEntity.notFound().build();
	    });
	}
	
	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<?> borrarCliente(@PathVariable Long id) {
		clienteRepositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
