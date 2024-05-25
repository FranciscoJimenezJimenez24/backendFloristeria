package com.backend.floristeria.modelo;

import java.time.LocalDate;

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
@Table(name="pedido")
public class Pedido {
	
	@Id @GeneratedValue
	private Long id_pedido;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente") // No es necesario el referencedColumnName aquí, asume la clave primaria de Cliente por defecto
	private Cliente cliente;
	
	private float total;
	
	private LocalDate fecha;
		
}
