package com.backend.floristeria.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cliente")
public class Cliente {
	
	@Id @GeneratedValue 
	private Long id_cliente;
	
	private String nombre;
	
	private String apellidos;
	
	private String direccion;
	
	private int cp;
	
	@ManyToOne
    @JoinColumn(name = "id")
	private User user;
	
	private String email;
	
	private String telefono;
	
	private String ciudad;
	
	private String forma_pago;

}
