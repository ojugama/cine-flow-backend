package co.edu.cineflow.peliculas;

import co.edu.cineflow.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Service
public class PeliculaImageStorage {
    private static final String PUBLIC_PATH = "/uploads/peliculas/";
    private static final Map<String, String> EXTENSIONES = Map.of(
            "image/jpeg", "jpg",
            "image/png", "png",
            "image/webp", "webp",
            "image/gif", "gif");

    private final Path directorio;

    public PeliculaImageStorage(@Value("${cineflow.uploads.dir:uploads}") String uploadDir) {
        this.directorio = Paths.get(uploadDir).toAbsolutePath().normalize().resolve("peliculas");
    }

    public String guardar(MultipartFile archivo, Long idPelicula) {
        if (archivo == null || archivo.isEmpty()) {
            throw new BusinessException("Selecciona una imagen para subir.");
        }

        String contentType = archivo.getContentType() == null
                ? ""
                : archivo.getContentType().toLowerCase(Locale.ROOT);
        String extension = EXTENSIONES.get(contentType);

        if (extension == null) {
            throw new BusinessException("Formato no permitido. Usa una imagen JPG, PNG, WEBP o GIF.");
        }

        try {
            Files.createDirectories(directorio);
            String nombre = "pelicula-" + idPelicula + "-" + UUID.randomUUID() + "." + extension;
            Path destino = directorio.resolve(nombre).normalize();
            Files.copy(archivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
            return PUBLIC_PATH + nombre;
        } catch (IOException ex) {
            throw new BusinessException("No se pudo guardar la imagen de la película.");
        }
    }

    public void eliminar(String imagenUrl) {
        if (imagenUrl == null || !imagenUrl.startsWith(PUBLIC_PATH)) {
            return;
        }

        String nombre = imagenUrl.substring(PUBLIC_PATH.length());
        if (nombre.isEmpty() || nombre.contains("/") || nombre.contains("\\")) {
            return;
        }

        try {
            Files.deleteIfExists(directorio.resolve(nombre).normalize());
        } catch (IOException ignored) {
        }
    }
}
