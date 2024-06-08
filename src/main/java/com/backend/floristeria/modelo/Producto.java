package com.backend.floristeria.modelo;

import java.io.Serializable;
import java.sql.Blob;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name="producto")
public class Producto{

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_producto;
	
	private String nombre;
	
	private String descripcion;
	
	private int stock;
	
	private String foto;
	
	private double precio;
	@ManyToOne
	@JoinColumn(name = "id_categoria") 
	private Categoria categoria;
		
	
}
