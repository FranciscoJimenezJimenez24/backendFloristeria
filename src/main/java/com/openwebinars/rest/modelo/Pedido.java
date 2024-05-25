package com.openwebinars.rest.modelo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Pedido {
	
	@Id 
	private Long id_pedido;
	
	private Long id_cliente;
	
	private float total;
	
	private LocalDate fecha;
		
}
