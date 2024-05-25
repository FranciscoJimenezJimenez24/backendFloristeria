package com.openwebinars.rest.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity

public class Usuario {
	
	@Id 
	private Long id_usuario;
	
	private String usuario;
	
	private String contrasena;
	
	private String rol;

}
