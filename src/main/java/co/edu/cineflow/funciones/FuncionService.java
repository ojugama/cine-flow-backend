package co.edu.cineflow.funciones;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FuncionService {
    FuncionEntity create(FuncionEntity in, Long idPelicula, Long idSala);

    FuncionEntity findById(Long id);

    Page<FuncionEntity> findAll(Pageable pageable);

    List<FuncionEntity> findByPelicula(Long idPelicula);

    FuncionEntity update(Long id, FuncionEntity in, Long idPelicula, Long idSala);

    void delete(Long id);
}