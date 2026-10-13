package co.edu.cineflow.usuarios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Page<UsuarioEntity> findAllByIsActivoTrue(Pageable pageable);

    Optional<UsuarioEntity> findByIdAndIsActivoTrue(Long id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<UsuarioEntity> findByEmailAndIsActivoTrue(String email);
}
