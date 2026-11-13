package co.edu.cineflow.funciones;

import java.time.LocalDateTime;

public interface FuncionRepositoryCustom {
    boolean existeCruceHorario(Long idSala, LocalDateTime inicio, LocalDateTime fin, Long idFuncion);
}
