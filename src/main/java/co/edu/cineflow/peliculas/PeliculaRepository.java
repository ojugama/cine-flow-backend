package co.edu.cineflow.peliculas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeliculaRepository extends JpaRepository<PeliculaEntity, Long> {
    Page<PeliculaEntity> findAllByIsActivoTrue(Pageable pageable);

    Optional<PeliculaEntity> findByIdAndIsActivoTrue(Long id);

    boolean existsByTituloAndIsActivoTrue(String titulo);

    boolean existsByTituloAndIdNotAndIsActivoTrue(String titulo, Long id);
}