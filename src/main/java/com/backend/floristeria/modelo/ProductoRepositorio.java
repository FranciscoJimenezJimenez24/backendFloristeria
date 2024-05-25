package com.backend.floristeria.modelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

	@Query("SELECT p FROM Producto p WHERE p.categoria.id_categoria = :id_categoria")
	List<Producto> buscarPorIdCategoria(@Param("id_categoria") Long id_categoria);

}
