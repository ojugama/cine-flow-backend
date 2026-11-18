package co.edu.cineflow.compras;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompraService {
    CompraEntity create(String email, CompraCreateDTO in);

    CompraEntity findById(Long id, String email, boolean isAdmin);

    List<CompraEntity> findByUsuario(String email);

    Page<CompraEntity> findAll(Pageable pageable);

    List<String> findSillasOcupadas(Long idFuncion);

    boolean existenComprasPorFuncion(Long idFuncion);
}
