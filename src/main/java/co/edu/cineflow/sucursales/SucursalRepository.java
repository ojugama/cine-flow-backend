package co.edu.cineflow.sucursales;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SucursalRepository extends MongoRepository<SucursalEntity, Long> {
}
