package co.edu.cineflow.combos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ComboDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer precio;
    private String icono;
    private Boolean isActivo;
}
