package co.edu.cineflow.peliculas;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "peliculas")
public class PeliculaEntity {
    @Id
    private Long id;

    private String titulo;

    private String sinopsis;

    private Integer duracionMinutos;

    private String genero;

    private Boolean isActivo = true;
}
