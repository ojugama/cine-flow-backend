package co.edu.cineflow.combos;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ComboRepository extends MongoRepository<ComboEntity, Long> {
    List<ComboEntity> findAllByIsActivoTrue(Sort sort);

    Optional<ComboEntity> findByIdAndIsActivoTrue(Long id);
}
