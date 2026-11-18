package co.edu.cineflow.compras;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompraHandler {
    private final CompraService compraService;
    private final CompraMapper compraMapper;

    public CompraHandler(CompraService compraService, CompraMapper compraMapper) {
        this.compraService = compraService;
        this.compraMapper = compraMapper;
    }

    public CompraDTO create(String email, CompraCreateDTO in) {
        CompraEntity saved = compraService.create(email, in);
        return compraMapper.toDTO(saved);
    }

    public CompraDTO findById(Long id, String email, boolean isAdmin) {
        return compraMapper.toDTO(compraService.findById(id, email, isAdmin));
    }

    public List<CompraDTO> findMyCompras(String email) {
        return compraService.findByUsuario(email).stream()
                .map(compraMapper::toDTO)
                .toList();
    }

    public Page<CompraDTO> findAll(Pageable pageable) {
        return compraService.findAll(pageable).map(compraMapper::toDTO);
    }

    public List<String> findSillasOcupadas(Long idFuncion) {
        return compraService.findSillasOcupadas(idFuncion);
    }
}
