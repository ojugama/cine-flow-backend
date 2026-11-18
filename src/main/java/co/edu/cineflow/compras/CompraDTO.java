package co.edu.cineflow.compras;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CompraDTO {
    private Long id;
    private String codigo;
    private Long idUsuario;
    private String emailUsuario;
    private Long idPelicula;
    private String tituloPelicula;
    private Long idFuncion;
    private LocalDateTime fechaHoraInicioFuncion;
    private LocalDateTime fechaHoraFinFuncion;
    private Long idSala;
    private String nombreSala;
    private String formatoSala;
    private String nombreSucursal;
    private String ciudadSucursal;
    private List<String> sillas;
    private Integer precioBoleta;
    private List<CompraComboDTO> combos;
    private Integer total;
    private LocalDateTime fechaCompra;
    private Boolean isVigente;
    private Boolean isActivo;
}
