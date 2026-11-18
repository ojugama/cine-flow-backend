package co.edu.cineflow.compras;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CompraComboCreateDTO {
    @NotNull(message = "El ID del combo es requerido.")
    private Long idCombo;

    @NotNull(message = "La cantidad del combo es requerida.")
    @Min(value = 1, message = "La cantidad del combo debe ser al menos 1.")
    private Integer cantidad;
}
