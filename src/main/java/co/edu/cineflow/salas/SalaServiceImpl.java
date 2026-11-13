package co.edu.cineflow.salas;

import co.edu.cineflow.common.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SalaServiceImpl implements SalaService {
    private final SalaRepository salaRepository;

    public SalaServiceImpl(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Override
    public Page<SalaEntity> findAll(Pageable pageable) {
        return salaRepository.findAll(pageable);
    }

    @Override
    public SalaEntity findById(Long id) {
        return salaRepository.findById(id).orElseThrow(() -> new NotFoundException("Sala no encontrada con ID " + id + "."));
    }
}
