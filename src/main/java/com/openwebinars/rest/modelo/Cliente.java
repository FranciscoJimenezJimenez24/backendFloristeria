package com.openwebinars.rest.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Cliente {
	
	@Id 
	private Long id_cliente;
	
	private String nombre;
	
	private String apellidos;
	
	private String direccion;
	
	private int cp;
	
	private Long id_usuario;
	
	private String email;
	
	private String telefono;
	
	private String ciudad;
	
	private String forma_pago;

}
