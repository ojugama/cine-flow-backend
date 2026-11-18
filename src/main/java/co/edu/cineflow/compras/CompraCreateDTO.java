package co.edu.cineflow.compras;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CompraCreateDTO {
    @NotNull(message = "El ID de la función es requerido.")
    private Long idFuncion;

    @NotEmpty(message = "Debes seleccionar al menos una silla.")
    @Size(min = 1, max = 10, message = "Puedes comprar entre 1 y 10 boletas por compra.")
    private List<@NotBlank(message = "El código de la silla no puede estar vacío.") String> sillas;

    @Valid
    private List<CompraComboCreateDTO> combos = new ArrayList<>();
}
