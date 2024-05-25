package com.openwebinars.rest.modelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
	
	@Query("SELECT * FROM producto WHERE id_categoria = :id_categoria")
    List<Producto> buscarPorIdCategoria(@Param("id_categoria") Long id_categoria);

}
