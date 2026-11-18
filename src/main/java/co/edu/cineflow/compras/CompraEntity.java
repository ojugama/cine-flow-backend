package co.edu.cineflow.compras;

import co.edu.cineflow.funciones.FuncionEntity;
import co.edu.cineflow.usuarios.UsuarioEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "compras")
public class CompraEntity {
    @Id
    private Long id;

    private String codigo;

    @DBRef
    private UsuarioEntity usuario;

    @DBRef
    private FuncionEntity funcion;

    private List<String> sillas = new ArrayList<>();

    private Integer precioBoleta;

    private List<CompraComboItem> combos = new ArrayList<>();

    private Integer total;

    private LocalDateTime fechaCompra;

    private Boolean isActivo = true;
}
