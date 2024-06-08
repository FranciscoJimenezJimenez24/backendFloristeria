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

import com.backend.floristeria.modelo.Cliente;
import com.backend.floristeria.modelo.ClienteRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ClienteController {
    
    private final ClienteRepositorio clienteRepositorio;
    
    @GetMapping("/cliente")
    public ResponseEntity<?> getClientes(){
        List<Cliente> result = clienteRepositorio.findAll();
        
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }
    
    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> obtenerUno(@PathVariable Long id) {
        return clienteRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
    
    @PostMapping("/cliente")
    public ResponseEntity<?> nuevoCliente(@RequestBody Cliente nuevo) {
        Cliente saved = clienteRepositorio.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @PutMapping("/cliente/{id}")
    public ResponseEntity<?> editarCliente(@RequestBody Cliente editar, @PathVariable Long id) {
        return clienteRepositorio.findById(id)
                .map(cliente -> {
                    cliente.setNombre(editar.getNombre());
                    cliente.setApellidos(editar.getApellidos());
                    cliente.setCiudad(editar.getCiudad());
                    cliente.setCp(editar.getCp());
                    cliente.setDireccion(editar.getDireccion());
                    cliente.setEmail(editar.getEmail());
                    cliente.setForma_pago(editar.getForma_pago());
                    cliente.setUser(editar.getUser());
                    cliente.setTelefono(editar.getTelefono());
                    return ResponseEntity.ok(clienteRepositorio.save(cliente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<?> borrarCliente(@PathVariable Long id) {
        clienteRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
