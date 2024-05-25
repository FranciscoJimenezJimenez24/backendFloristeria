package com.backend.floristeria.modelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepositorio extends JpaRepository<Carrito, Long>{
	List<Carrito> findByUserId(Long userId);
}
