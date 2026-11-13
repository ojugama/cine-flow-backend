package co.edu.cineflow.funciones;

import co.edu.cineflow.peliculas.PeliculaEntity;
import co.edu.cineflow.salas.SalaEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "funciones")
public class FuncionEntity {
    @Id
    private Long id;

    @DBRef
    private PeliculaEntity pelicula;

    @DBRef
    private SalaEntity sala;

    private LocalDateTime fechaHoraInicio;

    private LocalDateTime fechaHoraFin;

    private Boolean isActivo = true;
}
