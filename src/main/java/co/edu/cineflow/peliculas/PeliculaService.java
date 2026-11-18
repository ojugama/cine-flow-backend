package co.edu.cineflow.peliculas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeliculaService {
    PeliculaEntity create(PeliculaEntity in);

    PeliculaEntity findById(Long id);

    Page<PeliculaEntity> findAll(Pageable pageable);

    PeliculaEntity update(Long id, PeliculaEntity in);

    PeliculaEntity actualizarImagen(Long id, String imagenUrl);

    void delete(Long id);
}