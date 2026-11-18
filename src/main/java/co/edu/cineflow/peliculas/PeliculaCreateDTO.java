package co.edu.cineflow.peliculas;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PeliculaCreateDTO {
    @NotBlank(message = "El título es obligatorio.")
    @Size(max = 100, message = "El título debe tener máximo 100 caracteres.")
    private String titulo;

    private String sinopsis;

    @NotNull(message = "La duración en minutos es obligatoria.")
    @Min(value = 1, message = "La duración debe ser mayor a 0 minutos.")
    private Integer duracionMinutos;

    @Size(max = 100, message = "El género debe tener máximo 100 caracteres.")
    private String genero;

    @Size(max = 500, message = "La URL de la imagen debe tener máximo 500 caracteres.")
    private String imagenUrl;
}