package com.backend.floristeria.controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.floristeria.modelo.Categoria;
import com.backend.floristeria.modelo.CategoriaRepositorio;
import com.backend.floristeria.modelo.Producto;
import com.backend.floristeria.modelo.ProductoRepositorio;
import com.backend.floristeria.service.FileStorageService;

import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;

@MultipartConfig
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    private final ProductoRepositorio productoRepositorio;
    private final CategoriaRepositorio categoriaRepositorio;
    
    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/producto")
    public ResponseEntity<?> obtenerTodos() {
        List<Producto> productos = productoRepositorio.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(productos);
        }
    }

    @GetMapping("/producto/categoria/{idCategoria}")
    public ResponseEntity<?> obtenerCategoria(@PathVariable Long idCategoria) {
        List<Producto> productos = productoRepositorio.buscarPorIdCategoria(idCategoria);
        if (productos.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(productos);
        }
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<?> obtenerUno(@PathVariable Long id) {
        return productoRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

   @PostMapping("/producto/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
    	System.out.println("hola");
        fileStorageService.store(file);
        Map<String, String> response = new HashMap<>();
        response.put("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        response.put("path", file.getOriginalFilename());  // Path to store in database
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/producto")
    public ResponseEntity<?> nuevoProducto(@RequestBody Producto nuevo) {
        if (nuevo.getCategoria() != null) {
            Categoria categoria = categoriaRepositorio.findById(nuevo.getCategoria().getId_categoria()).orElse(null);
            nuevo.setCategoria(categoria);
        }
        Producto saved = productoRepositorio.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/producto/{id}")
    public ResponseEntity<?> editarProducto(@RequestBody Producto editar, @PathVariable Long id) {
        return productoRepositorio.findById(id)
                .map(producto -> {
                    if (productoRepositorio.existsById(id)) {
                        editar.setId_producto(id);
                        Producto updatedProducto = productoRepositorio.save(editar);
                        return ResponseEntity.ok(updatedProducto);
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/producto/{id}")
    public ResponseEntity<?> borrarProducto(@PathVariable Long id) {
        return productoRepositorio.findById(id)
                .map(producto -> {
                    productoRepositorio.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
