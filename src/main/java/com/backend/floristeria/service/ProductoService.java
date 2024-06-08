package com.backend.floristeria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.floristeria.modelo.Producto;
import com.backend.floristeria.modelo.ProductoRepositorio;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepositorio productoRepository;

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }
}