package co.edu.cineflow.combos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "combos")
public class ComboEntity {
    @Id
    private Long id;

    private String nombre;

    private String descripcion;

    private Integer precio;

    private String icono;

    private Boolean isActivo = true;
}
