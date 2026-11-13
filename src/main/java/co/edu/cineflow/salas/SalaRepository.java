package co.edu.cineflow.salas;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SalaRepository extends MongoRepository<SalaEntity, Long> {
}
