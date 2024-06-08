package com.backend.floristeria.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path rootLocation;

    public FileStorageService() {
        // Ajusta la ruta al directorio de carga en src/main/resources/upload-dir
        this.rootLocation = Paths.get("src/main/resources/upload-dir");
        try {
            // Verifica si `upload-dir` es un archivo en lugar de un directorio
            if (Files.exists(rootLocation) && !Files.isDirectory(rootLocation)) {
                throw new RuntimeException("upload-dir is not a directory");
            }
            // Crea el directorio si no existe
            if (Files.notExists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    public void store(MultipartFile file) {
        System.out.println("File: " + file);
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            System.out.println("No está vacio");
            System.out.println("Ruta del directorio de carga: " + rootLocation);

            Path destinationFile = this.rootLocation.resolve(
                Paths.get(file.getOriginalFilename()))
                .normalize().toAbsolutePath();
            System.out.println("Destino: " + destinationFile);

            // Ensure the file is saved within the root location
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory.");
            }

            Files.copy(file.getInputStream(), destinationFile);
            System.out.println("Archivo copiado exitosamente");
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }
}
