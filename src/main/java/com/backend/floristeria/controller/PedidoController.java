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

import com.backend.floristeria.modelo.PedidoRepositorio;
import com.backend.floristeria.modelo.Pedido;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = {"http://localhost:4200"})
public class PedidoController {
	
	private final PedidoRepositorio pedidoRepositorio;
	
	@GetMapping("/pedido")
	public ResponseEntity<?> getPedidos(){
		List<Pedido> result=pedidoRepositorio.findAll();
		
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(result);
		}
	}
	
	@GetMapping("/pedido/{id}")
	public ResponseEntity<?> obtenerUno(@PathVariable Long id) {
		Pedido result = pedidoRepositorio.findById(id).orElse(null);
		if (result == null)
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(result);
	}
	
	@PostMapping("/pedido")
	public ResponseEntity<?> nuevoPedido(@RequestBody Pedido nuevo) {
		Pedido saved = pedidoRepositorio.save(nuevo);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@PutMapping("/pedido/{id}")
	public ResponseEntity<?> editarPedido(@RequestBody Pedido editar, @PathVariable Long id) {
	    return pedidoRepositorio.findById(id).map((Pedido pedido) -> {
	        pedido.setCliente(pedido.getCliente());
	        pedido.setTotal(pedido.getTotal());
	        pedido.setFecha(pedido.getFecha());
	        return ResponseEntity.ok(pedidoRepositorio.save(pedido));
	    }).orElseGet(() -> {
	        return ResponseEntity.notFound().build();
	    });
	}
	
	@DeleteMapping("/pedido/{id}")
	public ResponseEntity<?> borrarPedido(@PathVariable Long id) {
		pedidoRepositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
