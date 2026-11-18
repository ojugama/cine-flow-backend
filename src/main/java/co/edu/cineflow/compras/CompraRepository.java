package co.edu.cineflow.compras;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompraRepository extends MongoRepository<CompraEntity, Long>, CompraRepositoryCustom {
    Page<CompraEntity> findAllByIsActivoTrue(Pageable pageable);

    Optional<CompraEntity> findByIdAndIsActivoTrue(Long id);

    @Query(value = "{ 'usuario.$id': ?0, 'isActivo': true }", sort = "{ 'fechaCompra': -1 }")
    List<CompraEntity> findByUsuarioIdAndIsActivoTrue(Long idUsuario);

    @Query("{ 'funcion.$id': ?0, 'isActivo': true }")
    List<CompraEntity> findByFuncionIdAndIsActivoTrue(Long idFuncion);
}
