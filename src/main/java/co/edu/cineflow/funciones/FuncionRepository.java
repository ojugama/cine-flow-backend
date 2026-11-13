package co.edu.cineflow.funciones;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FuncionRepository extends MongoRepository<FuncionEntity, Long>, FuncionRepositoryCustom {
    Page<FuncionEntity> findAllByIsActivoTrue(Pageable pageable);

    Optional<FuncionEntity> findByIdAndIsActivoTrue(Long id);

    @Query("{ 'pelicula.$id': ?0, 'isActivo': true }")
    List<FuncionEntity> findByPeliculaIdAndIsActivoTrue(Long idPelicula);
}
