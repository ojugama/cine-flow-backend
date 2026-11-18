package co.edu.cineflow.compras;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CompraComboItem {
    private Long idCombo;

    private String nombre;

    private Integer precio;

    private Integer cantidad;
}
