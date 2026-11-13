package co.edu.cineflow.salas;

import co.edu.cineflow.sucursales.SucursalEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "salas")
public class SalaEntity {
    @Id
    private Long id;

    @DBRef
    private SucursalEntity sucursal;

    private String nombre;

    private String formato;
}
