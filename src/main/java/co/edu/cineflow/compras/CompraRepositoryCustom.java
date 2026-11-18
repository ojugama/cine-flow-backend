package co.edu.cineflow.compras;

import java.util.List;

public interface CompraRepositoryCustom {
    boolean existeSillaOcupada(Long idFuncion, List<String> sillas);
}
