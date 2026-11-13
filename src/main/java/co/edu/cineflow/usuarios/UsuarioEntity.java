package co.edu.cineflow.usuarios;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "usuarios")
public class UsuarioEntity {
    @Id
    private Long id;

    private String email;

    private String password;

    private String rol;

    private String nombres;

    private String apellidos;

    private Boolean isActivo = true;
}
