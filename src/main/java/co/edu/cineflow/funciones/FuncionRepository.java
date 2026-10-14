package co.edu.cineflow.funciones;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FuncionRepository extends JpaRepository<FuncionEntity, Long> {
    Page<FuncionEntity> findAllByIsActivoTrue(Pageable pageable);

    Optional<FuncionEntity> findByIdAndIsActivoTrue(Long id);

    List<FuncionEntity> findByPeliculaIdAndIsActivoTrue(Long idPelicula);

    @Query("""
                SELECT COUNT(f) > 0 
                FROM FuncionEntity f 
                WHERE f.sala.id = :idSala 
                  AND f.isActivo = true 
                  AND (:idFuncion IS NULL OR f.id != :idFuncion)
                  AND (f.fechaHoraInicio < :fin AND f.fechaHoraFin > :inicio)
            """)
    boolean existeCruceHorario(Long idSala, LocalDateTime inicio, LocalDateTime fin, Long idFuncion);
}