package com.openwebinars.rest.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity

public class Carrito {
	
	@Id 
	private Long id_carrito;
	
	private Long id_cliente;
	
	private Long id_producto;

}
