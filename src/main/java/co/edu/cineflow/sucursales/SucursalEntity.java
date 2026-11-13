package co.edu.cineflow.sucursales;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "sucursales")
public class SucursalEntity {
    @Id
    private Long id;

    private String nombre;

    private String ciudad;
}
