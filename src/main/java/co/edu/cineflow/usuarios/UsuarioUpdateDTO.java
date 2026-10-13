package co.edu.cineflow.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioUpdateDTO {
    @NotBlank(message = "El email es obligatorio.")
    @Size(max = 100, message = "El email debe tener máximo 100 caracteres.")
    @Email(message = "El email debe tener un formato válido.")
    private String email;

    @NotBlank(message = "Los nombres son obligatorios.")
    @Size(max = 100, message = "Los nombres deben tener máximo 100 caracteres.")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios.")
    @Size(max = 100, message = "Los apellidos deben tener máximo 100 caracteres.")
    private String apellidos;
}
