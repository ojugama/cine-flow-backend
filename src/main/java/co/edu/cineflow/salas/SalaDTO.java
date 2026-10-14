package co.edu.cineflow.salas;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SalaDTO {
    private Long id;
    private Long idSucursal;
    private String nombreSucursal;
    private String ciudadSucursal;
    private String nombre;
    private String formato;
}
