package secureforge.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${upload.dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo crear el directorio para almacenar los archivos.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalizar el nombre del archivo
        String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName.replaceAll("\\s+", "_");

        try {
            // Comprobar si el nombre del archivo contiene caracteres inválidos
            if (fileName.contains("..")) {
                throw new RuntimeException("Nombre de archivo inválido: " + fileName);
            }

            // Copiar el archivo al directorio de destino (reemplazando si ya existe)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName; // Retorna el nombre del archivo guardado
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo almacenar el archivo " + fileName + ". Por favor, inténtalo de nuevo!", ex);
        }
    }
}