package com.openwebinars.rest.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Producto {

	@Id 
	private Long id_producto;
	
	private String nombre;
	
	private String descripcion;
	
	private int stock;
	
	private String foto;
	
	private Long id_categoria;
	
	
}
