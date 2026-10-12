package co.edu.cineflow.salas;

import co.edu.cineflow.sucursales.SucursalEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "salas")
public class SalaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal", nullable = false)
    private SucursalEntity sucursal;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "formato", length = 50, nullable = false)
    private String formato;
}
