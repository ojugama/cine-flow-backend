package co.edu.cineflow.peliculas;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PeliculaDTO {
    private Long id;
    private String titulo;
    private String sinopsis;
    private Integer duracionMinutos;
    private String genero;
    private String imagenUrl;
    private Boolean isActivo;
}