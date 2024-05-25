package com.backend.floristeria.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.floristeria.modelo.Categoria;
import com.backend.floristeria.modelo.CategoriaRepositorio;
import com.backend.floristeria.modelo.Producto;
import com.backend.floristeria.modelo.ProductoRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

	private final ProductoRepositorio productoRepositorio;
	private final CategoriaRepositorio categoriaRepositorio;

	/**
	 * Obtenemos todos los productos
	 * 
	 * @return
	 */
	@GetMapping("/producto")
	public List<Producto> obtenerTodos() {
		return productoRepositorio.findAll();
	}
	
	@GetMapping("/producto/categoria/{idCategoria}")
	public List<Producto> obtenerCategoria(@PathVariable Long idCategoria) {
		return productoRepositorio.buscarPorIdCategoria(idCategoria);
	}

	/**
	 * Obtenemos un producto en base a su ID
	 * 
	 * @param id
	 * @return Null si no encuentra el producto
	 */
	@GetMapping("/producto/{id}")
	public Producto obtenerUno(@PathVariable Long id) {
		return productoRepositorio.findById(id).orElse(null);
	}

	/**
	 * Insertamos un nuevo producto
	 * 
	 * @param nuevo
	 * @return producto insertado
	 */
	@PostMapping("/producto")
	public Producto nuevoProducto(@RequestBody Producto nuevo) {
		System.out.println("Método nuevoProducto() llamado con el siguiente Producto:");
	    System.out.println(nuevo);
	    if (nuevo.getCategoria() != null) {
	        Categoria categoria = categoriaRepositorio.findById(nuevo.getCategoria().getId_categoria()).orElse(null);
	        nuevo.setCategoria(categoria);
	    }
	    return productoRepositorio.save(nuevo);
	}


	/**
	 * 
	 * @param editar
	 * @param id
	 * @return
	 */
	@PutMapping("/producto/{id}")
	public Producto editarProducto(@RequestBody Producto editar, @PathVariable Long id) {
		if (productoRepositorio.existsById(id)) {
			editar.setId_producto(id);
			return productoRepositorio.save(editar);
		} else {
			return null;
		}
	}

	/**
	 * Borra un producto del catálogo en base a su id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/producto/{id}")
	public Producto borrarProducto(@PathVariable Long id) {
		if (productoRepositorio.existsById(id)) {
			Producto result = productoRepositorio.findById(id).get();
			productoRepositorio.deleteById(id);
			return result;
		} else
			return null;
	}

}
