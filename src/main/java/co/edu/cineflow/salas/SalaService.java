package co.edu.cineflow.salas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SalaService {
    Page<SalaEntity> findAll(Pageable pageable);

    SalaEntity findById(Long id);
}
