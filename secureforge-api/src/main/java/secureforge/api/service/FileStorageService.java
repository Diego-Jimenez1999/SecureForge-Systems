package secureforge.api.service;

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

    public FileStorageService(@Value("${upload.dir:uploads}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new IllegalStateException("No se pudo crear directorio de archivos", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String originalFileName = Objects.requireNonNull(file.getOriginalFilename(), "Nombre de archivo requerido");
        String fileName = UUID.randomUUID() + "_" + originalFileName.replaceAll("\\s+", "_");

        if (fileName.contains("..")) {
            throw new IllegalArgumentException("Nombre de archivo inválido");
        }

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new IllegalStateException("No se pudo almacenar el archivo", ex);
        }
    }
}
