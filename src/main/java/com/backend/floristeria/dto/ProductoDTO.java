package com.backend.floristeria.dto;

import java.sql.Blob;

import com.backend.floristeria.modelo.Categoria;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ProductoDTO {

	private Long id_producto;
	private String nombre;
	private String descripcion;
	private int stock;
	private byte[] foto;
	private double precio; 
	private Categoria categoria;
}
